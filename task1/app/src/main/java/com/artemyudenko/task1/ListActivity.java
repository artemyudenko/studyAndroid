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
import android.widget.Toast;

import com.artemyudenko.task1.adapter.ListAdapter;
import com.artemyudenko.task1.constants.Constants;
import com.artemyudenko.task1.model.Item;

import java.util.ArrayList;
import java.util.List;

import static com.artemyudenko.task1.constants.Constants.*;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    public static List<Item> items = null;

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

        Intent previous = getIntent();
        boolean editSuccess = previous.getBooleanExtra(EDIT_SUCCESS.getKey(), false);

        if (editSuccess) {
            Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();
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
            moveToAddActivity.putExtra(EDIT_NAME_KEY.getKey(), selectedItem.getName());
            moveToAddActivity.putExtra(EDIT_PRICE_KEY.getKey(), selectedItem.getPrice());
            moveToAddActivity.putExtra(EDIT_QUANTITY_KEY.getKey(), selectedItem.getQuantity());
            moveToAddActivity.putExtra(EDIT_CHECKED_KEY.getKey(), isChecked);
        }
        startActivity(moveToAddActivity);
    }

    private List<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>();
            items.add(new Item("Mleko", "3PLN", 3, true));
            items.add(new Item("Chleb", "3PLN", 3, true));
        }
        return items;
    }

    private void showDeleteConfirmationDialog(final Item selectedItem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String alertTitle = "Do you want to delete the item " + selectedItem.getName() + '?';
        alert.setTitle(alertTitle);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                items.remove(selectedItem);
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Do nothing
                    }
                });
        alert.show();
    }
}
