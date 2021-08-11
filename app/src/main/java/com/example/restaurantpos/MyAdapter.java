package com.example.restaurantpos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MyAdapter extends ArrayAdapter<View> {



    private int resourceLayout;
    private Context mContext;

    public MyAdapter(Context context, int resource, List<View> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View block  = getItem(position);
        return block;
    }
}
