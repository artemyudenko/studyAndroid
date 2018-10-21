package com.artemyudenko.task1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.artemyudenko.task1.constants.Constants;

public class PreferencesActivity extends AppCompatActivity {

    private Spinner dropdown;
    private SharedPreferences sharedPreferences;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        fillInDropdown();
    }

    public void onSaveClick(View view) {
        editText = findViewById(R.id.editText);
        String sizeToSave = editText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREFERENCES_VALUE_KEY.getKey(), sizeToSave);

        dropdown = findViewById(R.id.spinner);
        String selectedColor = dropdown.getSelectedItem().toString();
        editor.putString(Constants.PREFERENCE_COLOR_KEY.getKey(), selectedColor);

        editor.apply();
    }

    private void fillInDropdown() {
        String[] colors = {"red", "blue", "green"};
        dropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        dropdown.setAdapter(adapter);
    }
}
