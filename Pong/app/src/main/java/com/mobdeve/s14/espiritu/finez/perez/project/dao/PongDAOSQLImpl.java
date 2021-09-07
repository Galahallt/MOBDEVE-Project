package com.mobdeve.s14.espiritu.finez.perez.project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PongDAOSQLImpl implements PongDAO {
    private ProfileDatabase profileDatabase;
    private ScoreDatabase scoreDatabase;

    public PongDAOSQLImpl(Context context) {
        profileDatabase = new ProfileDatabase(context);
        scoreDatabase = new ScoreDatabase(context);
    }

    @Override
    public boolean addProfile(ProfileModel profile) {
        SQLiteDatabase db = profileDatabase.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ProfileDatabase.PROFILE_USERNAME, profile.getUsername());
        cv.put(ProfileDatabase.PROFILE_PASSWORD, profile.getPassword());

        long result = db.insert(ProfileDatabase.PROFILE_TABLE, null, cv);

        return result != -1;
    }

    @Override
    public ProfileModel checkExisting(String user) {
        String query = "SELECT * FROM " + ProfileDatabase.PROFILE_TABLE + " WHERE " + ProfileDatabase.PROFILE_USERNAME + "=\"" + user + "\"";
        Cursor cursor = profileDatabase.getReadableDatabase().rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return new ProfileModel(cursor.getString(1), cursor.getString(2));
        } else {
            return null;
        }
    }

    @Override
    public boolean addScore(ScoreModel score) {
        SQLiteDatabase db = scoreDatabase.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ScoreDatabase.SCORE_USERNAME, score.getUsername());
        cv.put(ScoreDatabase.SCORE_SCORE, score.getScore());
        cv.put(ScoreDatabase.SCORE_DATESCORE, score.getDateScore());

        long result = db.insert(ScoreDatabase.SCORE_TABLE, null, cv);

        return result != -1;
    }

    @Override
    public ArrayList<ScoreModel> getAllUserScores(String user) {
        String query = "SELECT * FROM " + ScoreDatabase.SCORE_TABLE + " WHERE " + ScoreDatabase.SCORE_USERNAME + "=\"" + user + "\"" + " ORDER BY " + ScoreDatabase.SCORE_SCORE + " DESC";
        Cursor cursor = scoreDatabase.getReadableDatabase().rawQuery(query, null);

        ArrayList<ScoreModel> userList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           userList.add(new ScoreModel(cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
           cursor.moveToNext();
        }

        return userList;
    }

    @Override
    public int getTotalGames(String user) {
        String query = "SELECT COUNT(" + ScoreDatabase.SCORE_USERNAME + ") FROM " + ScoreDatabase.SCORE_TABLE + " WHERE " + ScoreDatabase.SCORE_USERNAME + "=\"" + user + "\"";
        Cursor cursor = scoreDatabase.getReadableDatabase().rawQuery(query, null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    @Override
    public ArrayList<ScoreModel> getAllUserHighestScore() {
        String query = "SELECT DISTINCT ON (" + ScoreDatabase.SCORE_USERNAME + ") * FROM " + ScoreDatabase.SCORE_TABLE + " ORDER BY " + ScoreDatabase.SCORE_USERNAME + ", " + ScoreDatabase.SCORE_SCORE + " DESC";
        Cursor cursor = scoreDatabase.getReadableDatabase().rawQuery(query, null);

        ArrayList<ScoreModel> userList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            userList.add(new ScoreModel(cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
            cursor.moveToNext();
        }

        return userList;
    }
}
