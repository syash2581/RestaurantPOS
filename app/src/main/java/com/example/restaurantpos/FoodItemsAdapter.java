package com.example.restaurantpos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantpos.database.DataBaseHelper;

import java.util.List;

public class FoodItemsAdapter extends ArrayAdapter<View> {
    private int resourceLayout;
    private Context mContext;

    private DataBaseHelper dbHelper;
    private Button btnCatItemEdit;

    TextView tvCatName,tvFoodItemName,tvFoodItemPrice,tvFoodItemDescription;
    TextView tvFoodId,tvFoodCatId,tvCatNameFood;


    public FoodItemsAdapter(Context context, int resource, List<View> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        dbHelper = new DataBaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View foodItem  = getItem(position);

        tvFoodItemName = foodItem.findViewById(R.id.tvFoodItemName);
        tvFoodItemPrice = foodItem.findViewById(R.id.tvFoodItemPrice);
        tvFoodItemDescription = foodItem.findViewById(R.id.tvFoodItemDescription);
        tvFoodId = foodItem.findViewById(R.id.tvFoooooodId);
        tvFoodCatId = foodItem.findViewById(R.id.tvFoodCatId);
        tvCatNameFood = foodItem.findViewById(R.id.tvCatNameFood);


        if (foodItem != null) {
            /*btnCatItemEdit  = foodItem.findViewById(R.id.btnEditCatItem);

            btnCatItemEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int foodId = Integer.parseInt(tvFoodId.getText().toString());
                    String FoodName = tvFoodItemName.getText().toString();
                    String FoodPrice = tvFoodItemPrice.getText().toString();
                    String FoodDescription = tvFoodItemDescription.getText().toString();
                    int CatId = Integer.parseInt(tvFoodCatId.getText().toString());
                    String FoodCatName = tvCatNameFood.getText().toString();


                    Intent intent = new Intent(mContext,CategoryItemInput.class);

                    intent.putExtra("CatId",0);
//                    intent.putExtra("FoodId",foodId);
                    intent.putExtra("FoodName",FoodName);
                    intent.putExtra("FoodPrice",FoodPrice);
                    intent.putExtra("FoodDescription",FoodDescription);
//                    intent.putExtra("Category",FoodCatName);

                    mContext.startActivity(intent);
                }
            });*/
        }

        return foodItem;
    }
}
