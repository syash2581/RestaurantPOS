package com.example.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeHome extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;

    ListView lv ;
    ArrayAdapter<View> arrayAdapter;
    List<View> orders;
    private Button btnNewOrder,btnClosedOrders;


    String personName;
    int tableId;
    int blockId;
    String tableStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        dataBaseHelper = new DataBaseHelper(EmployeeHome.this);

        lv = findViewById(R.id.lvOrders);
        orders = new ArrayList<>();

        btnClosedOrders =findViewById(R.id.btnCompletedOrder);
        btnNewOrder= findViewById(R.id.btnNewOrder);

        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeHome.this,NewOrder.class);
                startActivity(intent);
            }
        });

        btnClosedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EmployeeHome.this,CompletedActivity.class);
                startActivity(intent1);
            }
        });


        Cursor c = dataBaseHelper.getOccupiedTablesData();
        while (c.moveToNext())
        {
            View v = getLayoutInflater().inflate(R.layout.order,null);
            TextView tv = v.findViewById(R.id.tvOrderName);
            TextView tvTable = v.findViewById(R.id.tvTableNum);
            TextView tvBlock = v.findViewById(R.id.tvBlockNum);
            TextView tvBlockHidden = v.findViewById(R.id.tvBlockNumHidden);
            TextView tvTableHidden = v.findViewById(R.id.tvTableNumHidden);

            personName = c.getString(4);
            tableId = c.getInt(0);
            blockId = c.getInt(3);
            tableStatus = c.getString(2);


            int tableNo = (tableId%9);

            if(tableNo==0)
                tableNo = 9;

            tv.setText(personName);
            tvBlockHidden.setText(String.valueOf(blockId));
            tvTableHidden.setText(String.valueOf(tableNo));


            Button btnCheckOut = v.findViewById(R.id.btnCheckOut);
            Button btnAddItem = v.findViewById(R.id.btnAddItem);



            btnCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(EmployeeHome.this,Payment.class);
                    intent.putExtra("Name",tv.getText().toString());
                    intent.putExtra("Block",tvBlockHidden.getText().toString());
                    intent.putExtra("Table",tvTableHidden.getText().toString());
                    startActivity(intent);
                }
            });

            btnAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EmployeeHome.this,EditOrder.class);
                    intent.putExtra("Name",tv.getText().toString());
                    intent.putExtra("Block",tvBlockHidden.getText().toString());
                    intent.putExtra("Table",tvTableHidden.getText().toString());
                    startActivity(intent);
                    finish();
                }
            });

            tvBlock.setText("B : "+ blockId);
            tvTable.setText("T : "+ tableNo);
            orders.add(v);
        }
        arrayAdapter = new OrderListAdapter(EmployeeHome.this, android.R.layout.simple_list_item_1,orders);
        lv.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EmployeeHome.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}