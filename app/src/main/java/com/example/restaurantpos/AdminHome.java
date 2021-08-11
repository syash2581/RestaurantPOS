package com.example.restaurantpos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurantpos.database.DataBaseHelper;
import com.google.android.material.tabs.TabLayout;

public class AdminHome extends AppCompatActivity {



    TabLayout tl;



    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdminHome.this.finish();
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);




       BlocksFragment bf = new BlocksFragment();
       CategoriesFragment cf = new CategoriesFragment();
       StaffFragment sf = new StaffFragment();


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.MyLayout,cf);
        fragmentTransaction.commit();

        tl = findViewById(R.id.tlAdmin);


        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {

                    case 1:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        /*fragmentTransaction.add(R.id.MyLayout,bf);*/
                        fragmentTransaction.replace(R.id.MyLayout,bf);

                        fragmentTransaction.commit();
                        break;
                    case 0:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        /*fragmentTransaction.add(R.id.MyLayout,cf);*/
                        fragmentTransaction.replace(R.id.MyLayout,cf);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        /*fragmentTransaction.add(R.id.MyLayout,sf);*/
                        fragmentTransaction.replace(R.id.MyLayout,sf);
                        fragmentTransaction.commit();
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}