package com.artemyudenko.task1.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.artemyudenko.task1.AddEditActivity;
import com.artemyudenko.task1.ListActivity;
import com.artemyudenko.task1.MainActivity;

public class NotiReceiver extends BroadcastReceiver {

    private int list = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        int location = intent.getIntExtra("LOCATION", 0);
        Intent transferIntent;
        if (location == list) {
            transferIntent = new Intent(context, ListActivity.class);
        } else {
            transferIntent = new Intent(context, MainActivity.class);
        }
        context.startActivity(transferIntent);
    }
}
