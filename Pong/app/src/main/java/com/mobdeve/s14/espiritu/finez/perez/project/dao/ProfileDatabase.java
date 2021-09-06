package com.mobdeve.s14.espiritu.finez.perez.project.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDatabase extends SQLiteOpenHelper {
    // DB info
    private static final String DATABASE_NAME = "PongProfile.db";
    private static final int DATABASE_VERSION = 1;

    // Column names
    public static final String PROFILE_TABLE = "profile_table";
    public static final String PROFILE_ID = "_id";
    public static final String PROFILE_USERNAME = "profile_username";
    public static final String PROFILE_PASSWORD = "profile_password";

    // Table information
    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + PROFILE_TABLE + " (" +
            PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROFILE_USERNAME + " TEXT, " +
            PROFILE_PASSWORD + " TEXT " +
            ");";

    private Context context;

    public ProfileDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String up_query = "DROP TABLE IF EXISTS " + PROFILE_TABLE;
        db.execSQL(up_query);
    }
}
