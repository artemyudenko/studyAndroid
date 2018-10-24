package com.artemyudenko.task1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.artemyudenko.task1.db.DBEnum.*;

class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME.getS(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME.getS() + "("
                + ID_COLUMN.getS() + "INTEGER PRIMARY KEY AUTOINCREMENT "
                + NAME_COLUMN.getS() + " TEXT "
                + PRICE_COLUMN.getS() + " TEXT "
                + QUANTITY_COLUMN.getS() + " INTEGER "
                + CHECKED_COLUMN.getS() + " INTEGER default 0 "
                + ACTIVE_COLUMN.getS() + " INTEGER default 0 ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME.getS());
        onCreate(db);
    }
}
