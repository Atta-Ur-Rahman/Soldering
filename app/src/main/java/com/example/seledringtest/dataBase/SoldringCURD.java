package com.example.seledringtest.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class SoldringCURD {

    SQLiteDatabase sqLiteDatabase;
    Context context;

    public SoldringCURD(Context context) {
        this.context = context;

        SolderingDataBase solderingDataBase = new SolderingDataBase(context);
        sqLiteDatabase = solderingDataBase.getWritableDatabase();
    }

    public void insertMemo(Context context, String strMemoName, String strPower) {

        ContentValues values = new ContentValues();
        values.put("MEMO_NAME", strMemoName);
        values.put("POWER", strPower);

        sqLiteDatabase.insert("MEMO_TABLE", null, values);

        Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();


    }
}
