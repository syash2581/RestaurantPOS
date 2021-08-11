package com.example.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EditBlock extends AppCompatActivity {

    DataBaseHelper myDb;

    ListView lv ;

    ArrayAdapter<View> arrayAdapter;
    List<View> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_block);

        myDb = new DataBaseHelper(this);

        lv = findViewById(R.id.lvTables);

        tables = new ArrayList<>();


        tables.clear();


        Intent intent = getIntent();
        int blockno = intent.getIntExtra("blockid",1);
        Cursor c = myDb.getAllTablesData(blockno);
        while(c.moveToNext())
        {

            int id = c.getInt(0);
            String tablename = c.getString(1);
            String status = c.getString(2);
            int blockid = c.getInt(3);

            /*public static final String COL_1 = "ID";
            public static final String COL_2 = "tablename";
            public static final String COL_3 = "status";
            public static final String COL_4 = "blockid";*/

            View table = getLayoutInflater().inflate(R.layout.table,null);
            TextView tv = table.findViewById(R.id.tvTableName);
            tv.setText(tablename+" : "+status);
            tables.add(table);
        }
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tables);
        arrayAdapter = new MyAdapter3(this,android.R.layout.simple_list_item_1,tables);

        lv.setAdapter(arrayAdapter);
    }
}