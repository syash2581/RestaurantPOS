package com.example.restaurantpos.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.restaurantpos.AddCategoryItem;

import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RES_POS.db";


    public static final String TABLE_NAME = "blocks";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "BLOCKNO";
    public static final String COL_3 = "BLOCKNAME";
    public static final String COL_4 = "BLOCKTABLECOUNT";

    private Context context;
    SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME +" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" INTEGER,"+
                COL_3+" TEXT,"+COL_4+" INTEGER)";
        db.execSQL(query);

        query = "create table "+ DataBaseHelperTables.TABLE_NAME +" ("+ DataBaseHelperTables.COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DataBaseHelperTables.COL_2+" TEXT,"
                + DataBaseHelperTables.COL_3+" TEXT,"
                +DataBaseHelperTables.COL_4+" INTEGER,"
                +DataBaseHelperTables.COL_5+" TEXT," +
                " FOREIGN KEY ("+DataBaseHelperTables.COL_4+") REFERENCES "+ DataBaseHelper.TABLE_NAME +" ("+ DataBaseHelper.COL_1 +"))";
        db.execSQL(query);


         query = "create table "+ DataBaseHelperCategory.TABLE_NAME +" ("+ DataBaseHelperCategory.COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +DataBaseHelperCategory.COL_2+" TEXT,"+
                DataBaseHelperCategory.COL_3+" INTEGER)";
         db.execSQL(query);

         query = "create table "+ DataBaseHelperStaff.TABLE_NAME +" ("+ DataBaseHelperStaff.COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +DataBaseHelperStaff.COL_2+" TEXT,"+
                DataBaseHelperStaff.COL_3+" INTEGER)";
         db.execSQL(query);

        query = "create table "+ DataBaseHelperFoodItem.TABLE_NAME +" ("+ DataBaseHelperFoodItem.COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DataBaseHelperFoodItem.COL_2+" TEXT,"
                +DataBaseHelperFoodItem.COL_3+" INTEGER,"
                +DataBaseHelperFoodItem.COL_4+" TEXT,"+
                DataBaseHelperFoodItem.COL_5+" INTEGER," +
                "FOREIGN KEY ("+DataBaseHelperFoodItem.COL_5+") REFERENCES "+ DataBaseHelperCategory.TABLE_NAME +" ("+ DataBaseHelperCategory.COL_1 +"))";
        db.execSQL(query);



        query = "create table "+ DataBaseHelperOrderEntries.TABLE_NAME +" ("+ DataBaseHelperOrderEntries.COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DataBaseHelperOrderEntries.COL_2+" INTEGER  REFERENCES "+ DataBaseHelper.TABLE_NAME +" ("+ DataBaseHelper.COL_1 +"),"
                +DataBaseHelperOrderEntries.COL_3+" INTEGER  REFERENCES "+ DataBaseHelperTables.TABLE_NAME +" ("+ DataBaseHelperTables.COL_1 +"),"
                +DataBaseHelperOrderEntries.COL_4+" INTEGER  REFERENCES "+ DataBaseHelperCategory.TABLE_NAME +" ("+ DataBaseHelperCategory.COL_1 +"),"
                + DataBaseHelperOrderEntries.COL_5+" INTEGER  REFERENCES "+ DataBaseHelperFoodItem.TABLE_NAME +" ("+ DataBaseHelperFoodItem.COL_1 +"),"
                + DataBaseHelperOrderEntries.COL_6+" INTEGER,"
                + DataBaseHelperOrderEntries.COL_7+" INTEGER,"
                + DataBaseHelperOrderEntries.COL_8+" TEXT,"
                + DataBaseHelperOrderEntries.COL_9+" INTEGER,"
                + DataBaseHelperOrderEntries.COL_10+" TEXT)";

        db.execSQL(query);


        query = "create table "+ DataBaseHelperClosedOrders.TABLE_NAME +" ("+ DataBaseHelperClosedOrders.COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DataBaseHelperClosedOrders.COL_2+" INTEGER  REFERENCES "+ DataBaseHelper.TABLE_NAME +" ("+ DataBaseHelper.COL_1 +"),"
                +DataBaseHelperClosedOrders.COL_3+" INTEGER  REFERENCES "+ DataBaseHelperTables.TABLE_NAME +" ("+ DataBaseHelperTables.COL_1 +"),"
                +DataBaseHelperClosedOrders.COL_4+" TEXT,"
                + DataBaseHelperClosedOrders.COL_5+" INTEGER,"
                + DataBaseHelperClosedOrders.COL_6+" INTEGER,"
                + DataBaseHelperClosedOrders.COL_7+" INTEGER,"
                + DataBaseHelperClosedOrders.COL_8+" TEXT)";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS "+DataBaseHelperTables.TABLE_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS "+DataBaseHelperCategory.TABLE_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS "+DataBaseHelperStaff.TABLE_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS "+DataBaseHelperFoodItem.TABLE_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS "+DataBaseHelperOrderEntries.TABLE_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS "+DataBaseHelperClosedOrders.TABLE_NAME;
        db.execSQL(query);


        onCreate(db);
    }

    public boolean insertData(int blockno,String blockname,int blocktablecount)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_2,blockno);
        cv.put(COL_3,blockname);
        cv.put(COL_4,blocktablecount);


        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            return false;
        else {

            for(int i=0;i<9;i++)
            {
                String tablename = "table "+(i+1);
                insertTableData(tablename,"free",blockno);
            }
            return true;
        }
    }

    public Cursor getAllData()
    {
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }

    public  boolean insertTableData(String tablename,String status,int blockid)
    {
        DataBaseHelperTables dataBaseHelperTables = new DataBaseHelperTables();
        return dataBaseHelperTables.insertTableData(db,tablename,status,blockid,"NA");
    }

    public Cursor getAllTablesData(int blockno)
    {
        DataBaseHelperTables dataBaseHelperTables = new DataBaseHelperTables();
        return  dataBaseHelperTables.getAllTablesData(db,blockno);
    }
    public Cursor getOccupiedTablesData()
    {
        DataBaseHelperTables dataBaseHelperTables = new DataBaseHelperTables();
        return  dataBaseHelperTables.getOccupiedTablesData(db);
    }
    public Cursor getFreeTablesData(int blockId)
    {
        DataBaseHelperTables dataBaseHelperTables = new DataBaseHelperTables();
        return  dataBaseHelperTables.getFreeTablesData(db,blockId);
    }
    public boolean updateTableData(int blockId,int tableId,String status,String occupiedBy)
    {
        DataBaseHelperTables dataBaseHelperTables = new DataBaseHelperTables();
        return  dataBaseHelperTables.updateTableData(db,blockId,tableId,status,occupiedBy);
    }


    public  boolean insertCategoryData( String catName, int noOfItems) {
        DataBaseHelperCategory dataBaseHelperCategory = new DataBaseHelperCategory();
        return dataBaseHelperCategory.insertCategoryData(db,catName,noOfItems);
    }
    public Cursor getAllCategoriesData()
    {
        DataBaseHelperCategory dataBaseHelperCategory = new DataBaseHelperCategory();
        return  dataBaseHelperCategory.getAllCategoriesData(db);
    }
    public boolean updateCatCount(int catid )
    {
        DataBaseHelperCategory dataBaseHelperCategory = new DataBaseHelperCategory();
        return  dataBaseHelperCategory.updateCatCount(db,catid);
    }

    public  boolean insertFoodItemData(String FoodName, int foodPrice,String foodDescription,int catid)
    {
        DataBaseHelperFoodItem dataBaseHelperFoodItem = new DataBaseHelperFoodItem();
        return dataBaseHelperFoodItem.insertFoodItemData(db,FoodName,foodPrice,foodDescription,catid);
    }
    public Cursor getAllFoodItemData()
    {
        DataBaseHelperFoodItem dataBaseHelperFoodItem = new DataBaseHelperFoodItem();
        return  dataBaseHelperFoodItem.getAllFoodItemData(db);
    }
    public Cursor getCatFoodItemData(int catid)
    {
        DataBaseHelperFoodItem dataBaseHelperFoodItem = new DataBaseHelperFoodItem();
        return  dataBaseHelperFoodItem.getCatFoodItemData(db,catid);
    }

    public boolean updateFoodItem(int foodId,int catId,String foodName,int foodPrice,String foodDescription)
    {
        DataBaseHelperFoodItem dataBaseHelperFoodItem = new DataBaseHelperFoodItem();
        return  dataBaseHelperFoodItem.updateFoodItem(db,foodId,catId,foodName,foodPrice,foodDescription);
    }

    public  boolean insertStaffData(String stname, String stmno)
    {
        DataBaseHelperStaff dataBaseHelperStaff = new DataBaseHelperStaff();
        return  dataBaseHelperStaff.insertStaffData(db,stname,stmno);
    }
    public Cursor getAllStaffData()
    {
        DataBaseHelperStaff dataBaseHelperStaff = new DataBaseHelperStaff();
        return  dataBaseHelperStaff.getAllStaffData(db);
    }

    public  boolean insertOrderEntry( int blockId, int tableId,int catId,int foodId,int qty,int itemPrice,String category)
    {
        DataBaseHelperOrderEntries dataBaseHelperOrderEntries = new DataBaseHelperOrderEntries();
        return  dataBaseHelperOrderEntries.insertOrderEntry(db,blockId,tableId,catId,foodId,qty,itemPrice,"current",category);
    }
    public Cursor getAllCurrentEntries(int blockId,int tableId)
    {
        DataBaseHelperOrderEntries dataBaseHelperOrderEntries = new DataBaseHelperOrderEntries();
        return  dataBaseHelperOrderEntries.getAllCurrentEntries(db,blockId,tableId);
    }
    public  boolean updateEntryStatusToCompleted(int blockId,int tableId,String personName)
    {
        DataBaseHelperOrderEntries dataBaseHelperOrderEntries = new DataBaseHelperOrderEntries();
        return  dataBaseHelperOrderEntries.updateEntryStatusToCompleted(db,blockId,tableId,"completed",personName);
    }


    public  boolean insertClosedOrder(int blockId, int tableId, String name, int noOfItems,int TotalAmt,String method)
    {
        DataBaseHelperClosedOrders dataBaseHelperClosedOrders = new DataBaseHelperClosedOrders();
        return  dataBaseHelperClosedOrders.insertClosedOrder(db,blockId,tableId,name,noOfItems, TotalAmt,"CASH");
    }
    public Cursor getAllClosedOrders()
    {
        DataBaseHelperClosedOrders dataBaseHelperClosedOrders = new DataBaseHelperClosedOrders();
        return  dataBaseHelperClosedOrders.getAllClosedOrders(db);
    }
}

/*
    Intent intent =  new Intent(getContext(), AddCategoryItem.class);
                    intent.putExtra("CategoryId",id);
                            intent.putExtra("Category",catName);
                            getContext().startActivity(intent);*/
