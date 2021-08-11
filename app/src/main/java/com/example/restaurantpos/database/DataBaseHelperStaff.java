package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelperStaff {
    public static final String TABLE_NAME = "staff";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "STNAME";
    public static final String COL_3 = "STMNO";

    public  boolean insertStaffData(SQLiteDatabase db, String stname, String stmno)
    {

        ContentValues cv = new ContentValues();
        cv.put(COL_2,stname);
        cv.put(COL_3,stmno);

        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllStaffData(SQLiteDatabase db )
    {
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }
}
