package com.example.linux.ws.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dashboard_DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dashboard.db";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "workshop_id";
    public static final String COL_3 = "workshop_title";


    public Dashboard_DatabaseHelper(Context context,String TABLE_NAME) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,WORKSHOP_ID TEXT,WORKSHOP_TITLE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
       // onCreate(sqLiteDatabase);

    }

    public void insert_data(int id,String title,String TABLE_NAME)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,id);
        contentValues.put(COL_3,title);
        long result = db.insert(TABLE_NAME,null ,contentValues);

    }

    public Cursor getWorkshopTitle(String TABLE_NAME)
    {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("select WORKSHOP_TITLE from "+TABLE_NAME,null);
        return result;
    }

    public boolean getItem(int i,String title,String TABLE_NAME)
    {
        String selectQuery = "select * from  " + TABLE_NAME + " where " +
                COL_2 + " = " + "'"+i+"'" + " and " + COL_3 + " = " + "'"+title+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    public void createUserTable(String user) {
        final SQLiteDatabase db = getWritableDatabase();
        String CREATE_TABLE_NEW_USER = "CREATE TABLE " + user + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,WORKSHOP_ID TEXT,WORKSHOP_TITLE TEXT)";
        db.execSQL(CREATE_TABLE_NEW_USER);
        db.close();
    }

}
