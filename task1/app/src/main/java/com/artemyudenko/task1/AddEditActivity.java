package com.artemyudenko.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.artemyudenko.task1.constants.Constants;
import com.artemyudenko.task1.db.DBManager;
import com.artemyudenko.task1.model.Item;

import static com.artemyudenko.task1.constants.Constants.ADD_SUCCESS;
import static com.artemyudenko.task1.constants.Constants.EDIT_CHECKED_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_NAME_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_PRICE_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_QUANTITY_KEY;
import static com.artemyudenko.task1.constants.Constants.EDIT_SUCCESS;

public class AddEditActivity extends AppCompatActivity {

    private DBManager dbManager;

    private TextView name;
    private TextView price;
    private TextView quantity;
    private CheckBox checkBox;

    private String previousName;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        name = findViewById(R.id.nameAddEdit);
        price = findViewById(R.id.priceAddEdit);
        quantity = findViewById(R.id.quantityAddEdit);
        checkBox = findViewById(R.id.checkBoxAddEdit);

        dbManager = new DBManager(this);

        init();
    }

    private void init() {
        Intent fromPrevious = getIntent();
        String name = fromPrevious.getStringExtra(EDIT_NAME_KEY.getKey());
        if (name != null) {
            this.previousName = name;
            this.id = fromPrevious.getLongExtra(Constants.EDIT_ID_KEY.getKey(), 0);

            String price = fromPrevious.getStringExtra(EDIT_PRICE_KEY.getKey());
            int quantity = fromPrevious.getIntExtra(EDIT_QUANTITY_KEY.getKey(), 0);
            boolean checked = fromPrevious.getBooleanExtra(EDIT_CHECKED_KEY.getKey(), false);

            this.name.setText(name);
            this.price.setText(price);
            this.quantity.setText(String.valueOf(quantity));
            this.checkBox.setChecked(checked);
        }
    }

    public void onAddEditSaveClick(View view) {
        boolean addPerformed = false;
        CharSequence nameText = name.getText();
        if (TextUtils.isEmpty(nameText)) {
            name.setError("Name is required!");
        } else {
            CharSequence priceText = price.getText();
            CharSequence quantityText = quantity.getText();
            boolean checked = checkBox.isChecked();

            int quantity = 0;

            if (!TextUtils.isEmpty(quantityText)) {
                quantity = Integer.parseInt(quantityText.toString());
            }

            Intent intent = new Intent(this, ListActivity.class);

            if (previousName != null) {
                Item item = constructItem(this.id, nameText.toString(), priceText.toString(),
                        quantity , checked);
                dbManager.open();
                dbManager.update(item, item.getId());
                intent.putExtra(EDIT_SUCCESS.getKey(), true);
            } else {
                dbManager.open();
                addPerformed = true;
                Item item = constructItem(0, nameText.toString(), priceText.toString(),
                        quantity, checked);
                dbManager.insert(item);
                intent.putExtra(ADD_SUCCESS.getKey(), true);
            }
            if (addPerformed) {
                Intent sharedIntent = new Intent();
                sharedIntent.setAction(Intent.ACTION_SEND);
                sharedIntent.putExtra(Intent.EXTRA_TEXT, "hello");
                sharedIntent.setType("text/plain");
                startActivity(sharedIntent);
            }
            dbManager.close();
            startActivity(intent);
        }
    }

    private Item constructItem(long id, String name, String price, int quantity, boolean checked) {
        return new Item(id, name, price, quantity, checked);
    }
}
