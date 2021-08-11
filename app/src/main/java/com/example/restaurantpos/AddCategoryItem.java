package com.example.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryItem extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;


    TextView tvCatName,tvFoodItemName,tvFoodItemPrice,tvFoodItemDescription;
    TextView tvFoodId,tvFoodCatId,tvCatNameFood;
    Button btnCatItemSave,btnCatItemAdd,btnCatItemEdit;
    List<View> catItems;
    MyAdapter3 myAdapter3;
    ListView lvCatItemsList;
    View vm;
    EditText etFoodItemName,etFoodItemPrice,etFoodItemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_item);

        dataBaseHelper = new DataBaseHelper(AddCategoryItem.this);


        catItems = new ArrayList<>();

        tvCatName = findViewById(R.id.tvCatNameSub);

        btnCatItemSave = findViewById(R.id.btnSaveCatItem);
        btnCatItemAdd = findViewById(R.id.btnAddCatItem);


        lvCatItemsList = findViewById(R.id.lvCatItems);


        Intent intent=getIntent();
        String s = intent.getStringExtra("Category");
        int catId = intent.getIntExtra("CategoryId",0);
        tvCatName.setText(s);





        catItems.clear();


        Cursor c = dataBaseHelper.getCatFoodItemData(catId);
        while(c.moveToNext())
        {
            int foodId = c.getInt(0);
            String FoodName = c.getString(1);
            int FoodPrice = c.getInt(2);
            String FoodDescription = c.getString(3);
            int CatId = c.getInt(4);


            /*public static final String COL_1 = "ID";
            public static final String COL_2 = "FoodName";
            public static final String COL_3 = "FoodPrice";
            public static final String COL_4 = "FoodDescription";
            public static final String COL_5 = "CatId";*/


            LayoutInflater inflater = getLayoutInflater();
            vm= inflater.inflate(R.layout.categoryitem,null);

            tvFoodItemName = vm.findViewById(R.id.tvFoodItemName);
            tvFoodItemPrice = vm.findViewById(R.id.tvFoodItemPrice);
            tvFoodItemDescription = vm.findViewById(R.id.tvFoodItemDescription);
            tvFoodCatId = vm.findViewById(R.id.tvFoodCatId);
            tvCatNameFood = vm.findViewById(R.id.tvCatNameFood);
            tvFoodId = vm.findViewById(R.id.tvFoooooodId);

            tvFoodItemName.setText(FoodName);
            tvFoodItemPrice.setText(String.valueOf(FoodPrice));
            tvFoodItemDescription.setText(FoodDescription);
            tvFoodCatId.setText(String.valueOf(catId));
            tvCatNameFood.setText(s);
            tvFoodId.setText(String.valueOf(foodId));


            btnCatItemEdit  = vm.findViewById(R.id.btnEditCatItem);

            btnCatItemEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(AddCategoryItem.this,CategoryItemInput.class);

                    intent1.putExtra("CatId",CatId+"");
                    intent1.putExtra("FoodId",foodId+"");
                    intent1.putExtra("FoodName",FoodName);
                    intent1.putExtra("FoodPrice",FoodPrice+"");
                    intent1.putExtra("FoodDescription",FoodDescription);
                    intent1.putExtra("Category",s);

                    startActivity(intent1);
                    finish();
                }
            });

            catItems.add(vm);
        }

        myAdapter3 = new MyAdapter3(AddCategoryItem.this, android.R.layout.simple_list_item_1,catItems);
        lvCatItemsList.setAdapter(myAdapter3);

        btnCatItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddCategoryItem.this,CategoryItemInput.class);

                intent.putExtra("CatId",catId+"");
                intent.putExtra("FoodId","");
                intent.putExtra("FoodName","");
                intent.putExtra("FoodPrice","");
                intent.putExtra("FoodDescription","");
                intent.putExtra("Category",s);

                startActivity(intent);
                finish();
            }
        });



        lvCatItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(AddCategoryItem.this,"Position : " +position,Toast.LENGTH_LONG ).show();
                View foodItem = catItems.get(position);

                tvFoodItemName = foodItem.findViewById(R.id.tvFoodItemName);
                tvFoodItemPrice = foodItem.findViewById(R.id.tvFoodItemPrice);
                tvFoodItemDescription = foodItem.findViewById(R.id.tvFoodItemDescription);
                tvFoodId = foodItem.findViewById(R.id.tvFoooooodId);
                tvFoodCatId = foodItem.findViewById(R.id.tvFoodCatId);
                tvCatNameFood = foodItem.findViewById(R.id.tvCatNameFood);


                if (foodItem != null) {
                    btnCatItemEdit  = foodItem.findViewById(R.id.btnEditCatItem);



                            int foodId = Integer.parseInt(tvFoodId.getText().toString());
                            String FoodName = tvFoodItemName.getText().toString();
                            String FoodPrice = tvFoodItemPrice.getText().toString();
                            String FoodDescription = tvFoodItemDescription.getText().toString();
                            int CatId = Integer.parseInt(tvFoodCatId.getText().toString());
                            String FoodCatName = tvCatNameFood.getText().toString();


                            Intent intent = new Intent(AddCategoryItem.this,CategoryItemInput.class);

                            intent.putExtra("CatId",CatId);
                            intent.putExtra("FoodId",foodId);
                            intent.putExtra("FoodName",FoodName);
                            intent.putExtra("FoodPrice",FoodPrice);
                            intent.putExtra("FoodDescription",FoodDescription);
                            intent.putExtra("Category",FoodCatName);

                            startActivity(intent);

                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCategoryItem.this,AdminHome.class);
        startActivity(intent);
        finish();
    }
}