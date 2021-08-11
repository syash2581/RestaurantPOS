package com.example.restaurantpos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.restaurantpos.database.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    ImageView fAdmin,fUser;
    DataBaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        myDb = new DataBaseHelper(this);

        fAdmin = findViewById(R.id.imgAdmin);
        fUser = findViewById(R.id.imgUser);

        fAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MyDialog.getInstance().showDialogBox(MainActivity.this,R.layout.activity_admin_mpin);
            }
        });

        fUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EmployeeHome.class);
                startActivity(intent);
                finish();
            }
        });
    }
}