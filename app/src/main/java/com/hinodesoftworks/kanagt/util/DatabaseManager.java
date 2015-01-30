package com.hinodesoftworks.kanagt.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseManager extends SQLiteAssetHelper {

    private static final String DB_NAME = "kana.db";
    private static final int DB_VERSION = 1;

    public DatabaseManager (Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    //crud

    //"query all" methods
    public Cursor getAllHira(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables("hiragana");
        Cursor c = qb.query(db, null, null, null, null, null, null);
        c.moveToFirst();

        db.close();

        return c;
    }

    public Cursor getAllKata(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables("katakana");
        Cursor c = qb.query(db, null, null, null, null, null, null);
        c.moveToFirst();

        db.close();

        return c;
    }


}
