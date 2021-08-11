package com.example.restaurantpos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantpos.database.DataBaseHelper;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment {
    private DataBaseHelper dataBaseHelper;

    private Button btnAddCatMain,btnCatList;


    private List<View> categories=new ArrayList<View>();
    private ArrayAdapter arrayAdapter;


    private TextView tvName,tvItemCount;

    private String catName;
    public CategoriesFragment() {
        // Required empty public constructor
    }



    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       dataBaseHelper = new DataBaseHelper(getContext());

        View v = inflater.inflate(R.layout.fragment_categories, container, false);


        btnAddCatMain = v.findViewById(R.id.btnAddCatMain);


        btnAddCatMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyDialog.getInstance().showDialogBox(getContext(),R.layout.activity_category_input);
                Intent intent = new Intent(getContext(),CategoryInput.class);
                startActivity(intent);
            }
        });

        loadData(inflater);

        arrayAdapter = new MyAdapter2(getContext(),android.R.layout.simple_list_item_1,categories);
        ListView lv= v.findViewById(R.id.lvCategory);
        lv.setAdapter(arrayAdapter);

        return v;
    }

    private void loadData(LayoutInflater inflater) {

        categories.clear();


        Cursor c = dataBaseHelper.getAllCategoriesData();


        while(c.moveToNext())
        {

            int id = c.getInt(0);
            String catName = c.getString(1);
            int noOfItems = c.getInt(2);

            /*public static final String COL_1 = "ID";
            public static final String COL_2 = "catName";
            public static final String COL_3 = "noOfItems";*/


            View category = inflater.inflate(R.layout.category,null);

            tvName = category.findViewById(R.id.tvCatName);
            tvItemCount = category.findViewById(R.id.tvItemCount);
            btnCatList = category.findViewById(R.id.btnCatList);


            tvName.setText(catName);
            tvItemCount.setText(""+noOfItems);


            btnCatList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(getContext(),AddCategoryItem.class);
                    intent.putExtra("CategoryId",id);
                    intent.putExtra("Category",catName);
                    getContext().startActivity(intent);
                }
            });

            categories.add(category);
        }


    }
}