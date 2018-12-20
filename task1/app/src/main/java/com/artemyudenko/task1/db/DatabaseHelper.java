package com.artemyudenko.task1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.artemyudenko.task1.db.DBEnum.*;

class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DB_NAME.getS(), null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME.getS() + " ("
                + ID_COLUMN.getS() + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COLUMN.getS() + " TEXT, "
                + DESCRIPTION_COLUMN.getS() + " TEXT, "
                + BRANCH_COLUMN.getS() + " TEXT, "
                + LATITUDE_COLUMN.getS() + " TEXT, "
                + LENGTH_COLUMN.getS() + " TEXT"
                + ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME.getS());
        onCreate(db);
    }
}
