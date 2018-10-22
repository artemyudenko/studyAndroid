package com.artemyudenko.task1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.artemyudenko.task1.adapter.ListAdapter;
import com.artemyudenko.task1.constants.Constants;
import com.artemyudenko.task1.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private static List<Item> items;

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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        int position = listAdapter.getPosition();

        Item selectedItem = items.get(position);

        if (Constants.EDIT.getKey().contentEquals(title)) {
            CheckBox checkBox = findViewById(R.id.checked);
            openEditAddActivity(selectedItem, checkBox.isChecked());
        } else {
            showDeleteConfirmationDialog(selectedItem.getName());
        }

        return true;
    }

    public void onAddClick(View view) {
        openEditAddActivity(null, false);
    }

    private void openEditAddActivity(Item selectedItem, boolean isChecked) {
        Intent moveToAddActivity = new Intent(this, AddEditActivity.class);
        if (selectedItem != null) {
            moveToAddActivity.putExtra(Constants.EDIT_NAME_KEY.getKey(), selectedItem.getName());
            moveToAddActivity.putExtra(Constants.EDIT_PRICE_KEY.getKey(), selectedItem.getPrice());
            moveToAddActivity.putExtra(Constants.EDIT_QUANTITY_KEY.getKey(), selectedItem.getQuantity());
            moveToAddActivity.putExtra(Constants.EDIT_CHECKED_KEY.getKey(), isChecked);
        }
        startActivity(moveToAddActivity);
    }

    private List<Item> getItems() {
        items = new ArrayList<>();
        items.add(new Item("Mleko", "3PLN", 3, true));
        items.add(new Item("Chleb", "3PLN", 3, true));
        return items;
    }

    private void showDeleteConfirmationDialog(String itemName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String alertTitle = "Do you want to delete the item " + itemName + '?';
        alert.setTitle(alertTitle);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }
}
