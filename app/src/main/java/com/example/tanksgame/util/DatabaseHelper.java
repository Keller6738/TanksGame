package com.example.tanksgame.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LONGEST_TIME = "longest_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_LONGEST_TIME + " INTEGER " +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrade the database if needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Registration: Insert a new user
    public boolean registerUser(String username, String email, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Check if username or email already exists
//        Cursor cursor = db.rawQuery(
//                "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?",
//                new String[]{username, email});
//
//        if (cursor.moveToFirst()) {
//            // User with the given username or email already exists.
//            cursor.close();
//            db.close();
//            return false; // or handle the error as needed
//        }
//
//        cursor.close();
//
//        // If not, insert new record
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USERNAME, username);
//        values.put(COLUMN_EMAIL, email);
//        values.put(COLUMN_PASSWORD, password);
//        values.put(COLUMN_LONGEST_TIME, 0);
//        long result = db.insert(TABLE_USERS, null, values);
//        db.close();
//        return result != -1;
        try (SQLiteDatabase db = this.getWritableDatabase(); Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?",
                new String[]{username, email})) {

            if (cursor.moveToFirst()) {
                return false;
            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, username);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_LONGEST_TIME, 0);
            return db.insert(TABLE_USERS, null, values) != -1;
        }
    }

    // Login: Check if user exists with given email and password
    public boolean loginUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    public void updateRecord(String username, int newRecord) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the new value
        ContentValues values = new ContentValues();
        values.put(COLUMN_LONGEST_TIME, newRecord);

        // Update the table
        int rowsAffected = db.update(TABLE_USERS, values, "username = ?", new String[]{username});

        // Check if the update was successful
        if (rowsAffected > 0) {
            Log.d("database", "User name updated successfully!");
        } else {
            Log.d("database", "Update failed: user not found. " + username);
        }

        // Close the database
        db.close();
    }

    public ArrayList<String> getAllUserNames(){
        ArrayList<String> players = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while (!cursor.isAfterLast())  {
            players.add(cursor.getString(1));
            cursor.moveToNext();
        }
        // closing connection
        cursor.close();
        return players;
    }

    public int getUsernameRecord(String username) {
        SQLiteDatabase db = this.getReadableDatabase();  // Changed to readable
        int record = 0;

        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_LONGEST_TIME},
                "username = ?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            record = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LONGEST_TIME));
            Log.d("database", "User record for " + username + ": " + record);
            cursor.close();
        }

        db.close();
        return record;
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_USERS, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
        return deletedRows > 0; // Returns true if at least one row was deleted
    }

    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.close();
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
