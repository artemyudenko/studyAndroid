package com.artemyudenko.task1;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.artemyudenko.task1.reciver.NotiReceiver;
import com.artemyudenko.task1.util.CommonUtil;

import static com.artemyudenko.task1.constants.Constants.PRFERENCES_NAME;

public class MainActivity extends AppCompatActivity {

    private NotiReceiver notiReceiver = new NotiReceiver();
    private static final String S_INTENT_FILTER = "sharedIntent2";
    private static final String CATEGORY = "sharedCat2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroad();
    }

    @Override
    public void onStart(){
        super.onStart();
        applyPrefs();
        registerBroad();
    }

    private void registerBroad() {
        IntentFilter intentFilter = new IntentFilter(S_INTENT_FILTER);
        intentFilter.addCategory(CATEGORY);
        registerReceiver(notiReceiver, intentFilter);
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
        Button btnList = findViewById(R.id.listButton);
        Button btnPref = findViewById(R.id.preferncesButton);

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
    
    private void clearPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PRFERENCES_NAME.getKey(),Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
