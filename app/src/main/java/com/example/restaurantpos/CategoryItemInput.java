package com.example.restaurantpos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantpos.database.DataBaseHelper;

public class CategoryItemInput extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    EditText etFoodName,etFoodPrice,etFoodDescription;
    Button btnAddFoodItem;

    String  foodName="";
    String foodPrice="";
    String foodDescription="";
    String category="";

    String catId="",foodId="";

    boolean isInEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryiteminput);

        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();

        catId = intent.getStringExtra("CatId");
        foodId = intent.getStringExtra("FoodId");
        foodName=intent.getStringExtra("FoodName");
        foodPrice=intent.getStringExtra("FoodPrice");
        foodDescription=intent.getStringExtra("FoodDescription");
        category = intent.getStringExtra("Category");


        etFoodDescription=findViewById(R.id.etFoodItemDescription);
        etFoodName=findViewById(R.id.etFoodItemName);
        etFoodPrice=findViewById(R.id.etFoodItemPrice);


        Log.d("ooooooooooooooooooooo",intent.getExtras().toString());
        if(foodId.equals("") && foodName.equals("") && foodPrice.equals("") && foodDescription.equals(""))
        {
            isInEdit=false;

        }
        else {

            isInEdit = true;
            etFoodName.setText(foodName);
            etFoodPrice.setText(foodPrice);
            etFoodDescription.setText(foodDescription);
        }




        btnAddFoodItem=findViewById(R.id.btnAddFoodItem);

        btnAddFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                foodName = etFoodName.getText().toString();
                foodPrice = etFoodPrice.getText().toString();
                foodDescription = etFoodDescription.getText().toString();

                if(isInEdit)
                {
                    catId = intent.getStringExtra("CatId");
                    foodId = intent.getStringExtra("FoodId");
                    dataBaseHelper.updateFoodItem(Integer.parseInt(foodId),Integer.parseInt(catId),foodName,Integer.parseInt(foodPrice),foodDescription);
                }
                else {
                    if(dataBaseHelper.insertFoodItemData(foodName,Integer.parseInt(foodPrice),foodDescription,Integer.parseInt(catId)))
                    {
                        dataBaseHelper.updateCatCount(Integer.parseInt(catId));
                    }
                }
                Intent intent1 = new Intent(CategoryItemInput.this,AddCategoryItem.class);

                catId = intent.getStringExtra("CatId");
                foodId = intent.getStringExtra("FoodId");
                category = intent.getStringExtra("Category");

                Log.d("iiiiiiiiiiiiii",intent.getExtras().toString());
                 intent1.putExtra("Category",category);
                 intent1.putExtra("CategoryId",Integer.parseInt(catId));

                 startActivity(intent1);
                 finish();
            }
        });
    }
}
