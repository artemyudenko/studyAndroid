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
        clearPreferences();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPreferencesClick(View view) {

    }

    public void onListClick(View view) {
        Intent moveToList = new Intent(this, ListActivity.class);
        startActivity(moveToList);
    }
    
    private void clearPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PRFERENCES_NAME.getKey(),Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
