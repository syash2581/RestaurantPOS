package com.example.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restaurantpos.database.DataBaseHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CompletedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(CompletedActivity.this);
        ListView lvClosedOrders = findViewById(R.id.lvClosedOrders);

        List<View> closedOrderList = new ArrayList<>();

        Cursor ords = dataBaseHelper.getAllClosedOrders();

        while (ords.moveToNext())
        {

            int Id  = ords.getInt(0);
            int BID = ords.getInt(1);
            int TID = ords.getInt(2);
            String personName = ords.getString(3);
            int noOfItems = ords.getInt(4);
            int TotalAmt = ords.getInt(5);
            String method = ords.getString(6);
            String date = ords.getString(7);

           /* public static final String COL_1 = "ID";
            public static final String COL_2 = "BID";
            public static final String COL_3 = "TID";
            public static final String COL_4 = "name";
            public static final String COL_5 = "noOfItems";
            public static final String COL_6 = "TotalAmt";
            public static final String COL_7 = "method";
            public static final String COL_8 = "dateOfClosed";*/

            LayoutInflater layoutInflater = getLayoutInflater();

            View orderItem = layoutInflater.inflate(R.layout.closedorder,null);

            TextView tvClosedOrderName,tvClosedOrderBlockNum,tvClosedOrderNumberOfItems,tvClosedOrderDate;
            TextView tvClosedOrderTotalAmt,tvClosedOrderTableNum,tvClosedOrderMethod;

            tvClosedOrderName = orderItem.findViewById(R.id.tvClosedOrderName);
            tvClosedOrderBlockNum = orderItem.findViewById(R.id.tvClosedOrderBlockNum);
            tvClosedOrderNumberOfItems = orderItem.findViewById(R.id.tvClosedOrderNumberOfItems);
            tvClosedOrderDate = orderItem.findViewById(R.id.tvClosedOrderDate);
            tvClosedOrderTotalAmt = orderItem.findViewById(R.id.tvClosedOrderTotalAmt);
            tvClosedOrderTableNum = orderItem.findViewById(R.id.tvClosedOrderTableNum);
            tvClosedOrderMethod = orderItem.findViewById(R.id.tvClosedOrderMethod);

            tvClosedOrderName.setText(personName);
            tvClosedOrderBlockNum.setText("B : "+String.valueOf(BID));
            tvClosedOrderNumberOfItems.setText("NOI : "+String.valueOf(noOfItems));
            tvClosedOrderDate.setText(date);
            tvClosedOrderTotalAmt.setText(String.valueOf(TotalAmt)+" ₹");
            tvClosedOrderTableNum.setText("T : "+String.valueOf(TID));
            tvClosedOrderMethod.setText(method);


            closedOrderList.add(orderItem);
        }

        MyAdapter3 arrayAdapter3 = new MyAdapter3(CompletedActivity.this, android.R.layout.simple_spinner_dropdown_item,closedOrderList);
        lvClosedOrders.setAdapter(arrayAdapter3);
    }
}