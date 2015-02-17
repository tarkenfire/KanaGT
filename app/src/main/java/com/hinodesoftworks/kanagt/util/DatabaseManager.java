package com.hinodesoftworks.kanagt.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import com.hinodesoftworks.kanagt.util.QuizManager.QuizMode;

public class DatabaseManager extends SQLiteAssetHelper {

    private static final String DB_NAME = "kana.db";
    private static final int DB_VERSION = 1;

    private Context ctx;

    public DatabaseManager (Context context){
        super (context, DB_NAME, null, DB_VERSION);
        ctx = context;
    }

    //reset database
    public void resetDatabases(){
        this.setForcedUpgrade(DB_VERSION);
        SQLiteDatabase db = getWritableDatabase();
        db.setVersion(-1);
        db.close();
        db = getWritableDatabase();
        db.close();
    }

    //"query" methods
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

    public Cursor getAllQuizResults(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables("quiz_stats");
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

    public Cursor getQuestionSet(String table, int numberOfQuestions, int classMin, int classMax){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        String[] projection = {"character", "roman"};

        Cursor c = qb.query(db,projection, "rowid>=" + classMin + " AND rowid<=" + classMax,
                                null, null, null, "Random()", "" + numberOfQuestions);
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

    public Cursor getCharacterStats(String table){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        String[] projection = {"prof_level", "times_correct", "times_incorrect"};
        Cursor c = qb.query(db, projection, null, null, null, null, null, null);

        c.moveToFirst();
        db.close();

        return c;
    }

    //update methods
    public void updateCharacterProf(String table, String charToUpdate, boolean isIncreased){
        //get character from table
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        String[] projection = {"prof_level", "times_correct", "times_incorrect"};
        Cursor c = qb.query(db, projection, "character='" + charToUpdate + "'", null, null, null,
                                null, null);

        c.moveToFirst();

        //check to see if the prof level is at max/min
        int profLevel = c.getInt(0);
        int correctTimes = c.getInt(1);
        int incorrectTimes = c.getInt(2);

        //mod prof levels
        if (isIncreased){
            profLevel++;
            correctTimes++;
        }
        else{
            profLevel--;
            incorrectTimes++;
        }

        if (profLevel >= 4){
            profLevel = 4;
        }
        else if (profLevel <= 1){
            profLevel = 1;
        }

        c.close();

        ContentValues values = new ContentValues();
        values.put("prof_level", profLevel);
        values.put("times_correct", correctTimes);
        values.put("times_incorrect", incorrectTimes);

        //update db
        db.update(table, values, "character='" + charToUpdate + "'", null);

        db.close();
    }

    //add methods
    public void addNewQuizResult(int correct, int incorrect, long timeTaken,
                                 QuizMode mode){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("quiz_time_taken", timeTaken);
        cv.put("date_taken_millis", System.currentTimeMillis());

        int total = correct + incorrect;
        int truncatedScore = (correct * 100) / total;

        cv.put("quiz_score", truncatedScore);

        String modeString = "";
        switch (mode){
            case MODE_HIRA_R_QUIZ:
                modeString = "Hiragana Ranking Quiz";
                break;
            case MODE_KATA_R_QUIZ:
                modeString = "Katakana Ranking Quiz";
                break;
            case MODE_HIRA_P_QUIZ:
                modeString = "Hiragana Practice Quiz";
                break;
            case MODE_KATA_P_QUIZ:
                modeString = "Katakana Practice Quiz";
                break;
        }

        cv.put("quiz_type", modeString);

        db.insert("quiz_stats",null, cv);
        db.close();
    }
}
