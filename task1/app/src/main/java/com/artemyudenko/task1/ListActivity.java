package com.artemyudenko.task1;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.artemyudenko.task1.adapter.ListAdapter;
import com.artemyudenko.task1.db.DBEnum;
import com.artemyudenko.task1.db.DBManagerLocal;
import com.artemyudenko.task1.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListAdapter listAdapter;
    private List<Item> items;
    private DBManagerLocal dbManagerLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView noItemView = findViewById(R.id.noItems);
        dbManagerLocal = new DBManagerLocal(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Item> items = getItems();

        if (items.isEmpty()) {
            noItemView.setVisibility(View.VISIBLE);
        } else {
            noItemView.setVisibility(View.INVISIBLE);
        }

        listAdapter = new ListAdapter(items);

        listAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        registerForContextMenu(recyclerView);

        Toast.makeText(this, "Added a new item", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = listAdapter.getPosition();
        showDeleteConfirmationDialog(items.get(position));
        return true;
    }

    public void onAddClick(View view) {
        Intent moveToAddActivity = new Intent(this, AddEditActivity.class);
        startActivity(moveToAddActivity);
    }

    private List<Item> getItems() {
        dbManagerLocal.open();
        items = new ArrayList<>();
        Cursor data = dbManagerLocal.fetch();
        if (data.moveToFirst()) {
            do {
                items.add( new Item(
                    data.getLong(data.getColumnIndex(DBEnum.ID_COLUMN.getS())),
                    data.getString(data.getColumnIndex(DBEnum.NAME_COLUMN.getS())),
                    data.getString(data.getColumnIndex(DBEnum.DESCRIPTION_COLUMN.getS())),
                    data.getString(data.getColumnIndex(DBEnum.BRANCH_COLUMN.getS()))
                ));
            } while (data.moveToNext());
        }
        dbManagerLocal.close();

        return items;
    }

    private void showDeleteConfirmationDialog(final Item selectedItem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String alertTitle = "Do you want to delete the item " + selectedItem.getName() + '?';
        alert.setTitle(alertTitle);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            dbManagerLocal.open();
            dbManagerLocal.delete(selectedItem.getId());
            int position = items.indexOf(selectedItem);
            items.remove(selectedItem);
            listAdapter.notifyItemRemoved(position);
            listAdapter.notifyItemRangeChanged(position, items.size());
            listAdapter.notifyDataSetChanged();
            dbManagerLocal.close();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        });

        alert.setNegativeButton("Cancel",
                (dialog, whichButton) -> {
                    //Do nothing
                });
        alert.show();
    }
}
