package com.artemyudenko.task1.db;

import com.artemyudenko.task1.model.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManagerCloud implements DBManager {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference items;

    @Override
    public DBManager open() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        items = firebaseDatabase.getReference("items");
        return this;
    }

    @Override
    public void close() {

    }

    @Override
    public void insert(Item item) {
        DatabaseReference push = items.push();
        String key = push.getKey();
        item.setKey(key);
        push.setValue(item);
    }

    @Override
    public int update(Item item, long id) {
        items.child(item.getKey()).setValue(item);
        return 0;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void delete(String key) {
        items.child(key).removeValue();
    }

    @Override
    public Object fetch() {
        List<Item> itemsList = new ArrayList<>();
        return itemsList;
    }

}
