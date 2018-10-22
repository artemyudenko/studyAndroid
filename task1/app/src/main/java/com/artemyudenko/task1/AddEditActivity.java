package com.artemyudenko.task1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.artemyudenko.task1.constants.Constants;

public class AddEditActivity extends AppCompatActivity {

    private TextView name;
    private TextView price;
    private TextView quantity;
    private CheckBox checkBox;

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
        String name = fromPrevious.getStringExtra(Constants.EDIT_NAME_KEY.getKey());
        if (name != null) {
            String price = fromPrevious.getStringExtra(Constants.EDIT_PRICE_KEY.getKey());
            int quantity = fromPrevious.getIntExtra(Constants.EDIT_QUANTITY_KEY.getKey(), 0);
            boolean checked = fromPrevious.getBooleanExtra(Constants.EDIT_CHECKED_KEY.getKey(), false);

            this.name.setText(name);
            this.price.setText(price);
            this.quantity.setText(String.valueOf(quantity));
            this.checkBox.setChecked(checked);
        }
    }
}
