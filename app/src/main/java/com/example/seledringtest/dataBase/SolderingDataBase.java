package com.example.seledringtest.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SolderingDataBase extends SQLiteOpenHelper {

    private static String DB_NAME = "DB_MEMO";
    private static int DB_VERSION = 1;


    public SolderingDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE MEMO_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMO_NAME,POWER)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
