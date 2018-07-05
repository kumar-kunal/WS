package com.example.linux.ws.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Events.db";
    public static final String TABLE_NAME = "Workshop";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Title";
    public static final String COL_3 = "Organiser";
    public static final String COL_4 = "Location";
    public static final String COL_5 = "Date";
    public static final String COL_6 = "Duration";
    public static final String COL_7 = "AppliedBy";
    public static final String COL_8="Description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,ORGANISER TEXT,LOCATION TEXT,DATE TEXT,DURATION TEXT,APPLIEDBY TEXT,DESCRIPTION TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public boolean insertData(String Title,String Organiser,String Location,String Date, String Duration,String AppliedBy,String Description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Title);
        contentValues.put(COL_3,Organiser);
        contentValues.put(COL_4,Location);
        contentValues.put(COL_5,Date);
        contentValues.put(COL_6,Duration);
        contentValues.put(COL_7,AppliedBy);
        contentValues.put(COL_8,Description);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDataFromDatabase()
    {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }

    public Cursor getDescriptionFromDatabase(int id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("select DESCRIPTION,ID from "+TABLE_NAME+" where "+
                "ID = "+id,null);

        return result;
    }

    public Cursor getTitleFromDatabase(int id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("select TITLE,ID from "+TABLE_NAME+" where "+
                "ID = "+id,null);

        return result;
    }

}
