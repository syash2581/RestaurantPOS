package com.example.restaurantpos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MyAdapter2 extends ArrayAdapter<View> {
private int resourceLayout;
private Context mContext;
private Button btnCatList;
private String catName;
private TextView tvName;
public MyAdapter2(Context context, int resource, List<View> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View category  = getItem(position);

        if (category != null) {
            /*btnCatList = category.findViewById(R.id.btnCatList);
            btnCatList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvName = category.findViewById(R.id.tvCatName);
                    catName = tvName.getText().toString();


                    Intent intent =  new Intent(getContext(),AddCategoryItem.class);
                    intent.putExtra("Category",catName);
                    getContext().startActivity(intent);
                }
            });*/
        }

        return category;
        }
}
