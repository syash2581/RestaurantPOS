package com.example.restaurantpos;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class StaffFragment extends Fragment {


    private DataBaseHelper dataBaseHelper;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> staff;


    public StaffFragment() {
        // Required empty public constructor
    }

    public static StaffFragment newInstance(String param1, String param2) {
        StaffFragment fragment = new StaffFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        staff  = new ArrayList<>();

        View v = inflater.inflate(R.layout.fragment_staff, container, false);

        listView = v.findViewById(R.id.lvStaffList);

        dataBaseHelper = new DataBaseHelper(getContext());

        loadData(getContext());

        EditText etStaffName = v.findViewById(R.id.stName);
        EditText etStaffMno=v.findViewById(R.id.stMno);

        Button btnAddStaff = v.findViewById(R.id.btnAddStaff);

        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stName=etStaffName.getText().toString();
                String stMno=etStaffMno.getText().toString();

                if(dataBaseHelper.insertStaffData(stName,stMno))
                {
                    loadData(getContext());
                }
            }
        });
        return v;
    }

    public void loadData(Context context)
    {
        staff.clear();

        Cursor c = dataBaseHelper.getAllStaffData();

        while (c.moveToNext())
        {
            String stName=c.getString(1);
            String stMno=c.getString(2);

            staff.add(stName+"      "+stMno);
        }
        arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,staff);
        listView.setAdapter(arrayAdapter);
    }
}