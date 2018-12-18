package com.artemyudenko.task1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.artemyudenko.task1.constants.Constants.PRFERENCES_NAME;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onListClick(View view) {
        Intent moveToList = new Intent(this, ListActivity.class);
        startActivity(moveToList);
    }

    public void onMapClick(View view) {
        Intent moveToMap = new Intent(this, Map.class);
        startActivity(moveToMap);
    }
}
