package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelperCategory {

    public static final String TABLE_NAME = "category";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "catName";
    public static final String COL_3 = "noOfItems";


    public  boolean insertCategoryData(SQLiteDatabase db, String catName, int noOfItems)
    {



        ContentValues cv = new ContentValues();
        cv.put(COL_2,catName);
        cv.put(COL_3,noOfItems);

        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllCategoriesData(SQLiteDatabase db )
    {
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
    public boolean updateCatCount(SQLiteDatabase db,int catid )
    {
        String query = "update category set "+COL_3+" = "+COL_3+" + 1 where "+COL_1+" = "+catid;
        db.execSQL(query);
        return true;
    }
}
