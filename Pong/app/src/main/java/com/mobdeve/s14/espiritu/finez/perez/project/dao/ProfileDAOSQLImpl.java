package com.mobdeve.s14.espiritu.finez.perez.project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobdeve.s14.espiritu.finez.perez.project.Profile;

import java.util.ArrayList;

public class ProfileDAOSQLImpl implements ProfileDAO {
    private SQLiteDatabase database;
    private ProfileDatabase profileDatabase;

    public ProfileDAOSQLImpl(Context context) {
        profileDatabase = new ProfileDatabase(context);
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

    public ArrayList<ProfileModel> checkExisting (String username) {
        String query = "SELECT * FROM " + ProfileDatabase.PROFILE_TABLE + " WHERE " + ProfileDatabase.PROFILE_USERNAME + "=\"" + username + "\"";
        Cursor cursor = profileDatabase.getReadableDatabase().rawQuery(query, null);

        ArrayList<ProfileModel> profList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            profList.add(new ProfileModel(cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }

        return profList;
    }
}
