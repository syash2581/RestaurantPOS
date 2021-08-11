package com.example.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.restaurantpos.database.DataBaseHelper;
import com.example.restaurantpos.database.DataBaseHelperOrderEntries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditOrder extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    TextView tvOrderPersonName , tvOrderBlockNum, tvOrderTableNum;

    ListView lvOrderList;

    Spinner spCategories,spItems;

    ArrayAdapter<String> arrayAdapter,arrayAdapter2;
    MyAdapter3 arrayAdapter3;



    Button btnAddInList,btnSaveOrder;
    EditText etQty;
    List<String> categories,items;

    List<Integer> catIds,itemIds,itemPrices,orderEntryIds;

    List<View> orderList;

    Map<String,Integer> foodItemsIds,foodItemsPrices;
    Map<Integer,String>  foodItemsNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        dataBaseHelper = new DataBaseHelper(EditOrder.this);

        categories = new ArrayList<>();
        items = new ArrayList<>();

        foodItemsIds = new HashMap<>();
        foodItemsPrices = new HashMap<>();
        foodItemsNames = new HashMap<>();


        catIds = new ArrayList<>();
        itemIds = new ArrayList<>();
        itemPrices = new ArrayList<>();
        orderEntryIds = new ArrayList<>();

        orderList = new ArrayList<View>();


        tvOrderPersonName = findViewById(R.id.tvOrderPersonName);
        tvOrderBlockNum = findViewById(R.id.tvOrderBlockNum);
        tvOrderTableNum = findViewById(R.id.tvOrderTableNum);

        spCategories = findViewById(R.id.spinOrderFoodCategory);
        spItems = findViewById(R.id.spinOrderFoodItem);

        btnAddInList = findViewById(R.id.btnAddEntry);
        btnSaveOrder = findViewById(R.id.btnSaveCatItem);

        etQty = findViewById(R.id.editTextNumber);

        lvOrderList = findViewById(R.id.lvInOrder);


        orderList.clear();
        /*---------------------------Setting TextView Data-------------------------*/
        Intent intent = getIntent();
        String orderPersonName = intent.getStringExtra("Name");
        String orderBlockNum = intent.getStringExtra("Block");
        String orderTableNum = intent.getStringExtra("Table");


        tvOrderPersonName.setText(orderPersonName);
        tvOrderBlockNum.setText("B : "+orderBlockNum);


        int tableNo = (Integer.parseInt(orderTableNum)%9);

        if(tableNo==0)
            tableNo = 9;

        tvOrderTableNum.setText("T : "+tableNo);
        /*---------------------------end Setting TextView Data-------------------------*/


        /*---------------------------getAllFoodItems-------------------------*/

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

        /*---------------------------end getAllFoodItems-------------------------*/

        /*---------------------------getAllCategoriesData-------------------------*/

        Cursor cats = dataBaseHelper.getAllCategoriesData();

        while (cats.moveToNext())
        {
            int catId = cats.getInt(0);
            String catName = cats.getString(1);
            int noOfItems = cats.getInt(2);

            categories.add(catName);
            catIds.add(catId);
        }
        items.clear();
        itemIds.clear();
        itemPrices.clear();

        /*---------------------------end getAllCategoriesData-------------------------*/


        /*--------------------------- getCatFoodItemData-------------------------*/

        Cursor its = dataBaseHelper.getCatFoodItemData(1);


        while (its.moveToNext())
        {

            int foodId  = its.getInt(0);
            String foodName = its.getString(1);
            int foodPrice = its.getInt(2);
            String foodDescription = its.getString(3);
            int foodCatId = its.getInt(4);

            items.add(foodName);
            itemIds.add(foodId);
            itemPrices.add(foodPrice);
        }
        /*--------------------------- end getCatFoodItemData-------------------------*/

        /*--------------------------- getOrderEntriesData-------------------------*/


        Cursor ords = dataBaseHelper.getAllCurrentEntries(Integer.parseInt(orderBlockNum),tableNo);

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


            items.add(foodItemsNames.get(foodId));
            itemIds.add(foodId);
            itemPrices.add(foodPrice);
            orderEntryIds.add(Id);



            String c = foodCatName;
            String i = foodItemsNames.get(foodId);
            int n = foodQty;


            LayoutInflater layoutInflater = getLayoutInflater();

            View orderItem = layoutInflater.inflate(R.layout.orderitem,null);

            TextView orderItemCategoryId = orderItem.findViewById(R.id.orderItemCategoryId);
            TextView orderItemCategory = orderItem.findViewById(R.id.orderItemCategory);
            TextView orderItemFoodItem = orderItem.findViewById(R.id.orderItemFoodItem);
            TextView orderItemQty = orderItem.findViewById(R.id.orderItemQty);
            ImageButton removeFoodItem = orderItem.findViewById(R.id.orderItemRemoveItem);
            removeFoodItem.setEnabled(false);


            orderItemCategoryId.setText(String.valueOf(foodId));
            orderItemCategory.setText(c);
            orderItemFoodItem.setText(foodItemsNames.get(foodId));
            orderItemQty.setText(""+n);


            orderList.add(orderItem);
        }


        /*---------------------------end  getOrderEntriesData-------------------------*/




        arrayAdapter = new ArrayAdapter<String>(EditOrder.this, android.R.layout.simple_spinner_dropdown_item,categories);
        arrayAdapter2 = new ArrayAdapter<String>(EditOrder.this, android.R.layout.simple_spinner_dropdown_item,items);
        arrayAdapter3 = new MyAdapter3(EditOrder.this, android.R.layout.simple_spinner_dropdown_item,orderList);


        spCategories.setAdapter(arrayAdapter);
        spItems.setAdapter(arrayAdapter2);
        lvOrderList.setAdapter(arrayAdapter3);




        spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                items.clear();
                itemIds.clear();
                itemPrices.clear();

                int catId = catIds.get(position);

                Cursor its = dataBaseHelper.getCatFoodItemData(catId);

                while (its.moveToNext())
                {
                    int foodId  = its.getInt(0);
                    String foodName = its.getString(1);
                    int foodPrice = its.getInt(2);
                    String foodDescription = its.getString(3);
                    int foodCatId = its.getInt(4);

                    items.add(foodName);
                    itemIds.add(foodId);
                    itemPrices.add(foodPrice);
                }

                arrayAdapter2 = new ArrayAdapter<String>(EditOrder.this, android.R.layout.simple_spinner_dropdown_item,items);
                spItems.setAdapter(arrayAdapter2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });















        btnAddInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = spCategories.getSelectedItem().toString();
                String i = spItems.getSelectedItem().toString();
                int n = Integer.parseInt(etQty.getText().toString());


                LayoutInflater layoutInflater = getLayoutInflater();

                View orderItem = layoutInflater.inflate(R.layout.orderitem,null);

                TextView orderItemCategoryId = orderItem.findViewById(R.id.orderItemCategoryId);
                TextView orderItemCategory = orderItem.findViewById(R.id.orderItemCategory);
                TextView orderItemFoodItem = orderItem.findViewById(R.id.orderItemFoodItem);
                TextView orderItemQty = orderItem.findViewById(R.id.orderItemQty);
                ImageButton removeFoodItem = orderItem.findViewById(R.id.orderItemRemoveItem);

                orderItemCategoryId.setText(String.valueOf(catIds.get(categories.indexOf(c))));
                orderItemCategory.setText(c);
                orderItemFoodItem.setText(i);
                orderItemQty.setText(""+n);

                removeFoodItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderList.remove(orderItem);
                        arrayAdapter3 = new MyAdapter3(EditOrder.this, android.R.layout.simple_spinner_dropdown_item,orderList);
                        lvOrderList.setAdapter(arrayAdapter3);
                    }
                });

                orderList.add(orderItem);
                arrayAdapter3 = new MyAdapter3(EditOrder.this, android.R.layout.simple_spinner_dropdown_item,orderList);
                lvOrderList.setAdapter(arrayAdapter3);

            }
        });






        btnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<orderList.size();i++)
                {

                    if(i<orderEntryIds.size())
                    {

                    }
                    else {

                        View dv = orderList.get(i);
                        TextView orderItemCategoryId = dv.findViewById(R.id.orderItemCategoryId);
                        TextView orderItemCategory = dv.findViewById(R.id.orderItemCategory);
                        TextView orderItemFoodItem = dv.findViewById(R.id.orderItemFoodItem);
                        TextView orderItemQty = dv.findViewById(R.id.orderItemQty);

                        int tableNo = (Integer.parseInt(orderTableNum)%9);

                        if(tableNo==0)
                            tableNo = 9;

                        tvOrderTableNum.setText("T : "+tableNo);

                        if(dataBaseHelper.insertOrderEntry(Integer.parseInt(orderBlockNum)
                                ,tableNo
                                ,Integer.parseInt(orderItemCategoryId.getText().toString())
                                ,foodItemsIds.get(orderItemFoodItem.getText().toString())
                                ,Integer.parseInt(orderItemQty.getText().toString())
                                ,foodItemsPrices.get(orderItemFoodItem.getText().toString())
                                ,orderItemCategory.getText().toString()))
                        {

                        }
                    }
                }
                Intent intent1 = new Intent(EditOrder.this,EmployeeHome.class);
                startActivity(intent1);
                orderList.clear();
                finish();
                /*orderList.clear();*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditOrder.this,EmployeeHome.class);
        startActivity(intent);
        finish();
    }
}