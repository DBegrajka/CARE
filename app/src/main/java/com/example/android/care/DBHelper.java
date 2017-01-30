package com.example.android.care;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by deepak on 1/30/17.
 */

public class DBHelper extends SQLiteOpenHelper  {

    public static final String DB_NAME = "my_app.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CONFIRM_PASSWORD = "confirm_password";

    /*
    Create Table User
     */

    public static final String CREATE_TABLE_USERS = "CREATE TABLE" + USER_TABLE + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + "TEXT,"
            + COLUMN_EMAIL + "TEXT,"
            + COLUMN_USERNAME + "TEXT,"
            + COLUMN_PASSWORD + "TEXT,"
            + COLUMN_CONFIRM_PASSWORD + "TEXT);";

    public DBHelper (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    /*
    Storing User detail in database
     */
    public void addUser(String name, String email_id, String username, String password, String confirm_password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email_id);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_CONFIRM_PASSWORD, confirm_password);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted" +id);
    }

}
