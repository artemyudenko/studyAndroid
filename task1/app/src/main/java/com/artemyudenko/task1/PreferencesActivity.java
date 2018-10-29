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
import com.artemyudenko.task1.util.CommonUtil;

import java.util.Arrays;
import java.util.List;

import static com.artemyudenko.task1.constants.Constants.PRFERENCES_NAME;

public class PreferencesActivity extends AppCompatActivity {

    private Spinner dropdown;
    private SharedPreferences sharedPreferences;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        sharedPreferences = getSharedPreferences(PRFERENCES_NAME.getKey(),Context.MODE_PRIVATE);
        editText = findViewById(R.id.editText);
        editText.setText(String.valueOf(CommonUtil.getPreferencesSize(sharedPreferences)));
        fillInDropdown(CommonUtil.getPreferencesDropDown(sharedPreferences));
    }

    public void onSaveClick(View view) {
        editText = findViewById(R.id.editText);
        String sizeToSave = editText.getText().toString();
        int size = 0;
        if (!sizeToSave.isEmpty()) {
            size = Integer.parseInt(sizeToSave);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.PREFERENCES_VALUE_KEY.getKey(), size);

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
