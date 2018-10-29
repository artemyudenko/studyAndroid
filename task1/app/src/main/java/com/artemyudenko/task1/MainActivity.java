package com.artemyudenko.task1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.artemyudenko.task1.util.CommonUtil;

import static com.artemyudenko.task1.constants.Constants.PRFERENCES_NAME;

public class MainActivity extends AppCompatActivity {

    private Button btnPref;
    private Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart(){
        super.onStart();
        applyPrefs();
    }

    public void onPreferencesClick(View view) {
        Intent moveToPreferences = new Intent(this, PreferencesActivity.class);
        startActivity(moveToPreferences);
    }

    public void onListClick(View view) {
        Intent moveToList = new Intent(this, ListActivity.class);
        startActivity(moveToList);
    }

    private void applyPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(PRFERENCES_NAME.getKey(),Context.MODE_PRIVATE);
        String preferencesDropDown = CommonUtil.getPreferencesDropDown(sharedPreferences);
        int preferencesSize = CommonUtil.getPreferencesSize(sharedPreferences);

        int nColor = getColor(preferencesDropDown);
        btnList = findViewById(R.id.listButton);
        btnPref = findViewById(R.id.preferncesButton);

        if (nColor != -1) {
            btnList.setBackgroundColor(nColor);
            btnPref.setBackgroundColor(nColor);
        }

        if (preferencesSize != 0) {
            btnList.setTextSize(preferencesSize);
            btnPref.setTextSize(preferencesSize);
        }
    }

    private int getColor(String color) {
        if ("Red".equals(color)) {
            return Color.RED;
        } else if ("Blue".equals(color)) {
            return Color.BLUE;
        } else if ("Green".equals(color)) {
            return Color.GREEN;
        }
        return -1;
    }
}
