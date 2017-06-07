package com.example.nikita.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhoneDataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhoneDB";
    public static final String PHONE_TABLE = "phones";

    public static final String KEY_ID = "_id";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_MODEL = "model";

    public PhoneDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PHONE_TABLE + "(" + KEY_ID
                + " integer primary key," + KEY_BRAND + " text," + KEY_MODEL + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + PHONE_TABLE);
        onCreate(db);
    }
}
