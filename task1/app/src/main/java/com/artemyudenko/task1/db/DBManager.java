package com.artemyudenko.task1.db;

import android.database.Cursor;

import com.artemyudenko.task1.model.Item;

public interface DBManager {

    DBManager open();

    void close();

    void insert(Item item);

    int update(Item item, long id);

    void delete(long id);

    Object fetch();
}
