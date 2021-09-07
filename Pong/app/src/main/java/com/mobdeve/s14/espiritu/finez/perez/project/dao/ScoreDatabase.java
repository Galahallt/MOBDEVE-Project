package com.mobdeve.s14.espiritu.finez.perez.project.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDatabase extends SQLiteOpenHelper {
    // DB info
    private static final String DATABASE_NAME = "PongScores.db";
    private static final int DATABASE_VERSION = 1;

    // Column names
    public static final String SCORE_TABLE = "score_table";
    public static final String SCORE_ID = "_id";
    public static final String SCORE_USERNAME = "score_username";
    public static final String SCORE_SCORE = "score_score";
    public static final String SCORE_DATESCORE = "score_datescore";

    // Table information
    private static final String CREATE_SCORE_TABLE = "CREATE TABLE " + SCORE_TABLE + " (" +
            SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SCORE_USERNAME + " TEXT, " +
            SCORE_SCORE + " TEXT, " +
            SCORE_DATESCORE + " TEXT " +
            ");";

    private Context context;

    public ScoreDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String up_query = "DROP TABLE IF EXISTS " + SCORE_TABLE;
        db.execSQL(up_query);
    }
}
