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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.artemyudenko.task1.adapter.ListAdapter;
import com.artemyudenko.task1.db.DBEnum;
import com.artemyudenko.task1.db.DBManager;
import com.artemyudenko.task1.model.Item;

import java.util.ArrayList;
import java.util.List;

import static com.artemyudenko.task1.constants.Constants.ADD_SUCCESS;
import static com.artemyudenko.task1.constants.Constants.EDIT;
import static com.artemyudenko.task1.constants.Constants.EDIT_CHECKED_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_ID_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_NAME_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_PRICE_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_QUANTITY_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_SUCCESS;

public class ListActivity extends AppCompatActivity {

    private ListAdapter listAdapter;
    private List<Item> items;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView noItemView = findViewById(R.id.noItems);
        dbManager = new DBManager(this);

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

        Intent previous = getIntent();
        boolean editSuccess = previous.getBooleanExtra(EDIT_SUCCESS.getKey(), false);
        boolean addSuccess = previous.getBooleanExtra(ADD_SUCCESS.getKey(), false);

        if (editSuccess) {
            Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();
        } else if (addSuccess) {
            Toast.makeText(this, "Added a new item", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        int position = listAdapter.getPosition();

        Item selectedItem = items.get(position);

        if (EDIT.getKey().contentEquals(title)) {
            CheckBox checkBox = findViewById(R.id.checked);
            openEditAddActivity(selectedItem, checkBox.isChecked());
        } else {
            showDeleteConfirmationDialog(selectedItem);
        }

        return true;
    }

    public void onAddClick(View view) {
        openEditAddActivity(null, false);
    }

    private void openEditAddActivity(Item selectedItem, boolean isChecked) {
        Intent moveToAddActivity = new Intent(this, AddEditActivity.class);
        if (selectedItem != null) {
            moveToAddActivity.putExtra(EDIT_ID_KEY.getKey(), selectedItem.getId());
            moveToAddActivity.putExtra(EDIT_NAME_KEY.getKey(), selectedItem.getName());
            moveToAddActivity.putExtra(EDIT_PRICE_KEY.getKey(), selectedItem.getPrice());
            moveToAddActivity.putExtra(EDIT_QUANTITY_KEY.getKey(), selectedItem.getQuantity());
            moveToAddActivity.putExtra(EDIT_CHECKED_KEY.getKey(), isChecked);
        }
        startActivity(moveToAddActivity);
    }

    private List<Item> getItems() {
        dbManager.open();
        items = new ArrayList<>();
        Cursor data = dbManager.fetch();
        if (data.moveToFirst()) {
            do {
                items.add( new Item(
                    data.getLong(data.getColumnIndex(DBEnum.ID_COLUMN.getS())),
                    data.getString(data.getColumnIndex(DBEnum.NAME_COLUMN.getS())),
                    data.getString(data.getColumnIndex(DBEnum.PRICE_COLUMN.getS())),
                    data.getInt(data.getColumnIndex(DBEnum.QUANTITY_COLUMN.getS())),
                    data.getInt(data.getColumnIndex(DBEnum.CHECKED_COLUMN.getS())) == 1
                ));
            } while (data.moveToNext());
        }
        dbManager.close();

        return items;
    }

    private void showDeleteConfirmationDialog(final Item selectedItem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String alertTitle = "Do you want to delete the item " + selectedItem.getName() + '?';
        alert.setTitle(alertTitle);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            dbManager.open();
            dbManager.delete(selectedItem.getId());
            int position = items.indexOf(selectedItem);
            items.remove(selectedItem);
            listAdapter.notifyItemRemoved(position);
            listAdapter.notifyItemRangeChanged(position, items.size());
            listAdapter.notifyDataSetChanged();
            dbManager.close();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        });

        alert.setNegativeButton("Cancel",
                (dialog, whichButton) -> {
                    //Do nothing
                });
        alert.show();
    }
}
