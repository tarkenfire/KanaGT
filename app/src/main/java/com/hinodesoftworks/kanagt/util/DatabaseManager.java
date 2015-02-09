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

    public Cursor getKanaDetails(String table, String character){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        Cursor c = qb.query(db, null, "character='" + character + "'", null, null, null, null);
        c.moveToFirst();
        db.close();

        return c;
    }

    public Cursor getQuestionSet(String table, int numberOfQuestions){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        String[] projection = {"character", "roman"};
        Cursor c = qb.query(db,projection, null, null, null, null, "Random()", "" + numberOfQuestions);
        c.moveToFirst();
        db.close();

        return c;
    }

    public Cursor getWrongAnswerRomanSet(String table, int numberOfQuestions, String answerToExclude){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        String[] projection = {"roman"};
        Cursor c = qb.query(db,projection, "character!='" + answerToExclude + "'", null, null, null,
                                "Random()", "" + numberOfQuestions);
        c.moveToFirst();
        db.close();

        return c;
    }


}
