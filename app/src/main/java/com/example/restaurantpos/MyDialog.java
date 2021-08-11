package com.example.restaurantpos;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantpos.database.DataBaseHelper;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;


public class MyDialog {
    private static MyDialog single_instance = null;

    private DataBaseHelper myDb;


    public static String catItemName="";
    public static String catItemPrice="";
    public static String catItemDescription="";


    public static String catName;

    private MyDialog()
    {

    }

    public static MyDialog getInstance()
    {
        if (single_instance == null)
            single_instance = new MyDialog();

        return single_instance;
    }
    public void showDialogBox(Context context,int id)
    {
        View dialog = LayoutInflater.from(context).inflate(id, null);
        final SwipeDismissDialog swipeDismissDialog = new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .build()
                .show();

        if(id==R.layout.activity_admin_mpin)
        {
            EditText etMPing = (EditText) dialog.findViewById(R.id.etMPin);
            Button Success = (Button) dialog.findViewById(R.id.btnSubmit);


            Success.setOnClickListener(v -> {


                String mpin = etMPing.getText().toString();
                if(mpin.equals("0000"))
                {
                    Intent intent = new Intent(context,AdminHome.class);
                    context.startActivity(intent);
                    
                }
                else {
                    etMPing.setText("");
                    Toast.makeText(context,"Incorrect Pin",Toast.LENGTH_LONG).show();
                }
            });

        }
        else if(id==R.layout.activity_category_input)
        {
            /*myDb = new DataBaseHelper(context);


             EditText etCatName;
             Button btnAdd;

            etCatName=dialog.findViewById(R.id.etCatName);
            btnAdd = dialog.findViewById(R.id.btnCatAdd2);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String catName = etCatName.getText().toString();

                    if(catName!=null && !catName.trim().equals(""))
                    {
                        myDb.insertCategoryData(catName,0);
                        swipeDismissDialog.dismiss();
                        *//*Intent intent = new Intent(context,AdminHome.class);
                        context.startActivity(intent);*//*
                    }
                }
            });*/


        }
        else if(id==R.layout.categoryiteminput)
        {
            EditText etFoodItemName,etFoodItemPrice,etFoodItemDescription;

            etFoodItemName = dialog.findViewById(R.id.etFoodItemName);
            etFoodItemPrice = dialog.findViewById(R.id.etFoodItemPrice);
            etFoodItemDescription = dialog.findViewById(R.id.etFoodItemDescription);

            if(catItemName.trim().equals("") && catItemPrice.trim().equals("") && catItemDescription.trim().equals(""))
                {
                    Button btnAddCatItem = dialog.findViewById(R.id.btnAddCatItem);
                    btnAddCatItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            catItemName = etFoodItemName.getText().toString().trim();
                            catItemPrice = etFoodItemPrice.getText().toString().trim();
                            catItemDescription=etFoodItemDescription.getText().toString().trim();
                        }
                    });
                }
                else
                {
                    etFoodItemName.setText(catItemName.trim());
                    etFoodItemPrice.setText(catItemPrice.trim());
                    etFoodItemDescription.setText(catItemDescription.trim());
                }
        }
    }
}

