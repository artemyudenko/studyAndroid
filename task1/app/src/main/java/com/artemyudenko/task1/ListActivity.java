package com.artemyudenko.task1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.artemyudenko.task1.adapter.ListAdapter;
import com.artemyudenko.task1.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(getItems());

        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        registerForContextMenu(recyclerView);
    }

    private List<Item> getItems(){
        List<Item> res  = new ArrayList<>();
        res.add(new Item("Mleko", "3PLN", 3, true));
        res.add(new Item("Chleb", "3PLN", 3, true));
        return res;
    }

    public void onAddClick(View view) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        int position = listAdapter.getPosition();
        return true;
    }
}
