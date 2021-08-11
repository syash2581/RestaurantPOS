package com.example.restaurantpos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    TextView tvName,tvBlock,tvTable;

    Map<String,Integer> foodItemsIds,foodItemsPrices;
    Map<Integer,String>  foodItemsNames;

    List<View> orderList;

    ListView lvOrderList;

    MyAdapter3 arrayAdapter3;

    int totalAmount=0;

    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        foodItemsIds = new HashMap<>();
        foodItemsPrices = new HashMap<>();
        foodItemsNames = new HashMap<>();

        orderList = new ArrayList<View>();

        dataBaseHelper = new DataBaseHelper(Payment.this);

        tvName = findViewById(R.id.tvPaymentName);
        tvBlock = findViewById(R.id.tvPaymentBlock);
        tvTable = findViewById(R.id.tvPaymentTable);

        btnPay = findViewById(R.id.btnFinalCheck);

        lvOrderList=findViewById(R.id.lvfinalBill);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String block = intent.getStringExtra("Block");
        String table = intent.getStringExtra("Table");

        tvName.setText(name);
        tvBlock.setText("B : "+block);
        tvTable.setText("T : "+table);


        Cursor foods = dataBaseHelper.getAllFoodItemData();

        while (foods.moveToNext())
        {
            int foodId = foods.getInt(0);
            String foodName = foods.getString(1);
            int foodPrice = foods.getInt(2);

            foodItemsIds.put(foodName,foodId);
            foodItemsPrices.put(foodName,foodPrice);
            foodItemsNames.put(foodId,foodName);
        }


        Cursor ords = dataBaseHelper.getAllCurrentEntries(Integer.parseInt(block),Integer.parseInt(table));

        while (ords.moveToNext())
        {

            int Id  = ords.getInt(0);
            int foodCatId = ords.getInt(3);
            int foodId = ords.getInt(4);
            int foodQty = ords.getInt(5);
            int foodPrice = ords.getInt(6);
            String foodCatName = ords.getString(9);


            /*public static final String COL_1 = "ID";
            public static final String COL_2 = "BID";
            public static final String COL_3 = "TID";
            public static final String COL_4 = "catId";
            public static final String COL_5 = "foodId";
            public static final String COL_6 = "qty";
            public static final String COL_7 = "itemPrice";
            public static final String COL_8 = "status";
            public static final String COL_9 = "AMT";*/

            LayoutInflater layoutInflater = getLayoutInflater();

            View orderItem = layoutInflater.inflate(R.layout.orderitem,null);

            TextView tvItemName,tvItemQty,tvItemPrice;

            tvItemName = orderItem.findViewById(R.id.orderItemCategory);
            tvItemQty =orderItem.findViewById(R.id.orderItemFoodItem);
            tvItemPrice =orderItem.findViewById(R.id.orderItemQty);

            ImageButton removeFoodItem = orderItem.findViewById(R.id.orderItemRemoveItem);
            removeFoodItem.setEnabled(false);
            removeFoodItem.setVisibility(View.INVISIBLE);

            String p=foodQty+" * "+foodPrice;
            tvItemName.setText(foodItemsNames.get(foodId));
            tvItemQty.setText(p);
            tvItemPrice.setText(String.valueOf(foodPrice*foodQty));

            totalAmount = totalAmount + (foodPrice*foodQty);
            orderList.add(orderItem);
        }

        arrayAdapter3 = new MyAdapter3(Payment.this, android.R.layout.simple_spinner_dropdown_item,orderList);
        lvOrderList.setAdapter(arrayAdapter3);

        btnPay.setText("PAY "+totalAmount+" ₹");
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.updateEntryStatusToCompleted(Integer.parseInt(block),Integer.parseInt(table),name);
                dataBaseHelper.updateTableData(Integer.parseInt(block),((Integer.parseInt(block)-1)*9)+Integer.parseInt(table),"free","NA");



                new AlertDialog.Builder(Payment.this)
                        .setTitle("Confirmation")
                        .setMessage("Payment Completed of "+totalAmount+" ₹.\n  Your Order Is Closed Now.")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Payment.this,EmployeeHome.class);
                                startActivity(intent1);
                                finish();
                            }
                        })
                        //.setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });

    }
}