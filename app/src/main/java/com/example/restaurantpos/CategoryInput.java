package com.example.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurantpos.database.DataBaseHelper;

public class CategoryInput extends AppCompatActivity {

    private DataBaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_input);


        myDb = new DataBaseHelper(CategoryInput.this);


        EditText etCatName;
        Button btnAdd;

        etCatName=findViewById(R.id.etCatName);
        btnAdd = findViewById(R.id.btnCatAdd2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String catName = etCatName.getText().toString();

                if(catName!=null && !catName.trim().equals(""))
                {
                    myDb.insertCategoryData(catName,0);
                    Intent intent = new Intent(CategoryInput.this,AdminHome.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CategoryInput.this,AdminHome.class);
        startActivity(intent);
        finish();
    }
}