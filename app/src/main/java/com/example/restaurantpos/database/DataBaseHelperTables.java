package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DataBaseHelperTables {



    public static final String TABLE_NAME = "tables";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "tablename";
    public static final String COL_3 = "status";
    public static final String COL_4 = "blockid";
    public static final String COL_5 = "occupiedby";

    public  boolean insertTableData(SQLiteDatabase db,String tablename,String status,int blockid,String occupiedby)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_2,tablename);
        cv.put(COL_3,status);
        cv.put(COL_4,blockid);
        cv.put(COL_5,occupiedby);


        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllTablesData(SQLiteDatabase db,int blockno)
    {
        String query = "select * from "+TABLE_NAME+" where "+ COL_4+" = "+blockno;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
    public Cursor getOccupiedTablesData(SQLiteDatabase db)
    {
        String query = "select * from "+TABLE_NAME+" where "+ COL_3+" = \'occupied\'";
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
    public Cursor getFreeTablesData(SQLiteDatabase db,int blockId)
    {
        String query = "select * from "+TABLE_NAME+" where "+COL_4+" = "+blockId+ " and "  + COL_3+" = \'free\'";
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
    public boolean updateTableData(SQLiteDatabase db,int blockId,int tableId,String status,String occupiedBy)
    {
        ContentValues cv = new ContentValues();

        cv.put(COL_3,status);
        cv.put(COL_5,occupiedBy);

        Log.d("uuuuuuuuuuuuuuuu", "updateTableData: "+blockId+""+tableId+""+cv.toString());
        int result = db.update(TABLE_NAME,cv,COL_1+"=?",new String[]{tableId+""});

        if(result==-1)
            return false;
        else
            return true;
    }
}
