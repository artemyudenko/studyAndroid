package com.artemyudenko.task1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startService(new Intent(this, LocationNotiService.class));
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
