package com.artemyudenko.task1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.artemyudenko.task1.constants.Constants;

import java.util.Arrays;
import java.util.List;

public class PreferencesActivity extends AppCompatActivity {

    private Spinner dropdown;
    private SharedPreferences sharedPreferences;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editText = findViewById(R.id.editText);

        String defaulSavedTextPrefs = "Size";
        String savedTextPref = sharedPreferences.getString(Constants.PREFERENCES_VALUE_KEY.getKey(),
                defaulSavedTextPrefs);
        editText.setText(savedTextPref);

        String savedDropDown = sharedPreferences.getString(Constants.PREFERENCES_VALUE_KEY.getKey(),
                "");
        fillInDropdown(savedDropDown);
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

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void fillInDropdown(String savedDropDown) {
        List colors = Arrays.asList( "Red", "Blue", "Green");
        dropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, (String [])colors.toArray());
        dropdown.setAdapter(adapter);
        if (!savedDropDown.isEmpty()) {
            int i = colors.indexOf(savedDropDown);
            dropdown.setSelection(i);
        }
    }
}
