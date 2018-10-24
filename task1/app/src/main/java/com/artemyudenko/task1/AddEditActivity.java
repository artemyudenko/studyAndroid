package com.artemyudenko.task1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.artemyudenko.task1.constants.Constants;
import com.artemyudenko.task1.model.Item;

import static com.artemyudenko.task1.ListActivity.items;
import static com.artemyudenko.task1.constants.Constants.*;
import static com.artemyudenko.task1.constants.Constants.EDIT_SUCCESS;

public class AddEditActivity extends AppCompatActivity {

    private TextView name;
    private TextView price;
    private TextView quantity;
    private CheckBox checkBox;

    private String previousName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        name = findViewById(R.id.nameAddEdit);
        price = findViewById(R.id.priceAddEdit);
        quantity = findViewById(R.id.quantityAddEdit);
        checkBox = findViewById(R.id.checkBoxAddEdit);

        init();
    }

    private void init() {
        Intent fromPrevious = getIntent();
        String name = fromPrevious.getStringExtra(EDIT_NAME_KEY.getKey());
        if (name != null) {
            previousName = name;
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
        CharSequence nameText = name.getText();
        CharSequence priceText = price.getText();
        CharSequence quantityText = quantity.getText();
        boolean checked = checkBox.isChecked();

        Intent intent = new Intent(this, ListActivity.class);

        if (previousName != null) {
            for (Item i:items) {
                if (i.getName().contentEquals(previousName)) {
                    int index = items.indexOf(i);
                    Item item = constructItem(nameText.toString(), priceText.toString(),
                            Integer.parseInt(quantityText.toString()), checked);
                    items.set(index, item);
                    break;
                }
            }
            intent.putExtra(EDIT_SUCCESS.getKey(), true);
        } else {
            Item item = constructItem(nameText.toString(), priceText.toString(),
                    Integer.parseInt(quantityText.toString()), checked);
            items.add(item);
            intent.putExtra(ADD_SUCCESS.getKey(), true);
        }
        startActivity(intent);
    }

    private Item constructItem(String name, String price, int quantity, boolean checked) {
        return new Item(name, price, quantity, checked);
    }
}
