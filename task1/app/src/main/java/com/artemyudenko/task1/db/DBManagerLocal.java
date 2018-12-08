package com.artemyudenko.task1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.artemyudenko.task1.model.Item;

import static com.artemyudenko.task1.db.DBEnum.*;

public class DBManagerLocal implements DBManager {
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManagerLocal(Context c) {
        this.context = c;
    }

    @Override
    public DBManagerLocal open() {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        databaseHelper.close();
    }

    @Override
    public void insert(Item item) {
        ContentValues contentValues = constructDBObject(item);
        database.insert(TABLE_NAME.getS(), null, contentValues);
    }

    @Override
    public Cursor fetch() {
        Cursor cursor = database.query(TABLE_NAME.getS(), getColumnNamesArray(), null, null, null, null, NAME_COLUMN.getS());
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    @Override
    public int update(Item item, long id) {
        ContentValues contentValues = constructDBObject(item);
        return database.update(TABLE_NAME.getS(), contentValues, ID_COLUMN.getS() + '=' + id, null);
    }

    @Override
    public void delete(long id) {
        database.delete(TABLE_NAME.getS(), ID_COLUMN.getS() + '=' + id, null);
    }

    @Override
    public void delete(String key) {

    }

    private ContentValues constructDBObject(Item item) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(NAME_COLUMN.getS(), item.getName());
        contentValue.put(PRICE_COLUMN.getS(), item.getPrice());
        contentValue.put(QUANTITY_COLUMN.getS(), item.getQuantity());
        contentValue.put(CHECKED_COLUMN.getS(), item.isChecked());
        return contentValue;
    }
}
