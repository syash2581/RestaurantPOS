package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseHelperFoodItem {


    public static final String TABLE_NAME = "fooditem";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "FoodName";
    public static final String COL_3 = "FoodPrice";
    public static final String COL_4 = "FoodDescription";
    public static final String COL_5 = "CatId";

    public  boolean insertFoodItemData(SQLiteDatabase db, String FoodName, int foodPrice,String foodDescription,int catid)
    {



        ContentValues cv = new ContentValues();
        cv.put(COL_2,FoodName);
        cv.put(COL_3,foodPrice);
        cv.put(COL_4,foodDescription);
        cv.put(COL_5,catid);

        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllFoodItemData(SQLiteDatabase db )
    {
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
    public Cursor getCatFoodItemData(SQLiteDatabase db,int catid)
    {
        String query = "select * from "+TABLE_NAME+" where "+COL_5+" = "+catid;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }

    public boolean updateFoodItem(SQLiteDatabase db,int foodId,int catId,String foodName,int foodPrice,String foodDescription)
    {
        ContentValues cv = new ContentValues();

        cv.put(COL_2,foodName);
        cv.put(COL_3,foodPrice);
        cv.put(COL_4,foodDescription);


//        Log.d("uuuuuuuuuuuuuuuu", "updateFoodItem: "+catId+""+cv.toString()+""+foodId);
        int result = db.update(TABLE_NAME,cv,COL_1+"=? and "+COL_5+"=?",new String[]{foodId+"",catId+""});

        if(result==-1)
            return false;
        else
            return true;
    }





}
