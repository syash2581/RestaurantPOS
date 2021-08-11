package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseHelperOrderEntries {


    public static final String TABLE_NAME = "OrderSingleEntry";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "BID";
    public static final String COL_3 = "TID";
    public static final String COL_4 = "catId";
    public static final String COL_5 = "foodId";
    public static final String COL_6 = "qty";
    public static final String COL_7 = "itemPrice";
    public static final String COL_8 = "status";
    public static final String COL_9 = "AMT";
    public static final String COL_10 = "catname";

    public  boolean insertOrderEntry(SQLiteDatabase db, int blockId, int tableId,int catId,int foodId,int qty,int itemPrice,String status,String category)
    {



        ContentValues cv = new ContentValues();
        cv.put(COL_2,blockId);
        cv.put(COL_3,tableId);
        cv.put(COL_4,catId);
        cv.put(COL_5,foodId);
        cv.put(COL_6,qty);
        cv.put(COL_7,itemPrice);
        cv.put(COL_8,status);
        cv.put(COL_9,qty*itemPrice);
        cv.put(COL_10,category);


        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllCurrentEntries(SQLiteDatabase db,int blockId,int tableId)
    {
        String query = "select * from "+TABLE_NAME+" where "+ COL_2+" = "+blockId+" and "+ COL_3+" = "+ tableId+" and "+COL_8+" = \'current\'";
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
    public  boolean updateEntryStatusToCompleted(SQLiteDatabase db,int blockId,int tableId,String status,String personName)
    {
        int totalAmount = 0;
        Cursor c = getAllCurrentEntries(db,blockId,tableId);
        int noOfItems = c.getCount();
        while (c.moveToNext()) {
            totalAmount = totalAmount + c.getInt(8);
        }

        ContentValues cv = new ContentValues();
        cv.put(COL_8,status);

        int result = db.update(TABLE_NAME,cv,COL_2+"=? and "+COL_3+"=? and "+COL_8+"=\'current\'",new String[]{blockId+"",tableId+""});

        if(result==-1)
            return false;
        else {
            DataBaseHelperClosedOrders dataBaseHelperClosedOrders = new DataBaseHelperClosedOrders();
            dataBaseHelperClosedOrders.insertClosedOrder(db,blockId,tableId,personName,noOfItems,totalAmount,"CASH");
            return true;
        }
    }
}
