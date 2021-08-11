package com.example.restaurantpos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NewOrder extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    List<Integer> blocks;
    List<Integer> tables;

    Spinner spinBlocks;
    Spinner spinTables;


    Button btnSaveOrder;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        dataBaseHelper = new DataBaseHelper(NewOrder.this);

        spinBlocks = findViewById(R.id.spinBlock);
        spinTables = findViewById(R.id.spinTable);

        btnSaveOrder =findViewById(R.id.btnSaveOrder);


        blocks = new ArrayList<>();
        tables = new ArrayList<>();


        Cursor b = dataBaseHelper.getAllData();
        while (b.moveToNext())
        {
            int blockId = b.getInt(0);
            if(!blocks.contains(blockId))
                blocks.add(blockId);

        }
        Cursor c = dataBaseHelper.getFreeTablesData(1);
        while (c.moveToNext())
        {
            int tableId = c.getInt(0);

            int tableNo = (tableId%9);

            if(tableNo==0)
                tables.add(9);
            if(tableNo!=0 && !tables.contains(tableNo))
                tables.add(tableNo);
        }


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(NewOrder.this,android.R.layout.simple_spinner_dropdown_item,blocks);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(NewOrder.this,android.R.layout.simple_spinner_dropdown_item,tables);


        spinBlocks.setAdapter(arrayAdapter1);
        spinTables.setAdapter(arrayAdapter2);


        spinBlocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selBlockId = blocks.get(spinBlocks.getSelectedItemPosition());
                Cursor cb = dataBaseHelper.getFreeTablesData(selBlockId);

                tables.clear();
                while (cb.moveToNext())
                {
                    int tableId = cb.getInt(0);

                    int tableNo = (tableId%9);

                    if(tableNo==0)
                        tables.add(9);
                    if(tableNo!=0 && !tables.contains(tableNo))
                        tables.add(tableNo);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etPersonName = findViewById(R.id.etPersonName);

                int selBlockId = blocks.get(spinBlocks.getSelectedItemPosition());
                int selTableID = tables.get(spinTables.getSelectedItemPosition());
                int finalSelTableId = ((selBlockId-1)*9)+selTableID;

                if (dataBaseHelper.updateTableData(selBlockId, finalSelTableId, "occupied", etPersonName.getText().toString())) {

                    Intent intent = new Intent(NewOrder.this, EmployeeHome.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

}
/*

tables.sort(new Comparator<Integer>() {
@Override
public int compare(Integer o1, Integer o2) {
        return o1>o2?o2:o1;
        }
        });*/
