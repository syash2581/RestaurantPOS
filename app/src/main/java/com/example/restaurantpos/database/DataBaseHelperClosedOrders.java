package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class DataBaseHelperClosedOrders {

    public static final String TABLE_NAME = "closedorders";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "BID";
    public static final String COL_3 = "TID";
    public static final String COL_4 = "name";
    public static final String COL_5 = "noOfItems";
    public static final String COL_6 = "TotalAmt";
    public static final String COL_7 = "method";
    public static final String COL_8 = "dateOfClosed";

    public  boolean insertClosedOrder(SQLiteDatabase db, int blockId, int tableId, String name, int noOfItems,int TotalAmt,String method)
    {



        ContentValues cv = new ContentValues();
        cv.put(COL_2,blockId);
        cv.put(COL_3,tableId);
        cv.put(COL_4,name);
        cv.put(COL_5,noOfItems);
        cv.put(COL_6,TotalAmt);
        cv.put(COL_7,method);
        cv.put(COL_8,new Date().toString());

        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllClosedOrders(SQLiteDatabase db)
    {
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
}
