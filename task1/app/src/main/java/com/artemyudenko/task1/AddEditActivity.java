package com.artemyudenko.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.artemyudenko.task1.db.DBManager;
import com.artemyudenko.task1.db.DBManagerLocal;
import com.artemyudenko.task1.model.Item;

public class AddEditActivity extends AppCompatActivity {

    private DBManager dbManager;

    private TextView name;
    private TextView descriprion;
    private TextView branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        name = findViewById(R.id.nameAddEdit);
        descriprion = findViewById(R.id.descriptionAdd);
        branch = findViewById(R.id.branchAdd);

        dbManager = new DBManagerLocal(this);
    }


    public void onAddEditSaveClick(View view) {
        CharSequence nameText = name.getText();
        if (TextUtils.isEmpty(nameText)) {
            name.setError("Name is required!");
        } else {
            CharSequence descriprionText = descriprion.getText();
            CharSequence branchText = branch.getText();
            Intent intent = new Intent(this, ListActivity.class);
            dbManager.open();
            Item item = constructItem(0, nameText.toString(), descriprionText.toString(),
                    branchText.toString());
            dbManager.insert(item);
            dbManager.close();
            startActivity(intent);
        }
    }

    private Item constructItem(long id, String name, String description, String branch) {
        return new Item(id, name, description, branch);
    }
}
