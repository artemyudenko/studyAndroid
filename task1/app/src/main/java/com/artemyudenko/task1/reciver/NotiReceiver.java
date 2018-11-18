package com.artemyudenko.task1.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.artemyudenko.task1.AddEditActivity;
import com.artemyudenko.task1.ListActivity;
import com.artemyudenko.task1.MainActivity;

public class NotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int location = intent.getIntExtra("LOCATION", 0);
        Intent intent1;
        if (location == 2) {
            intent1 = new Intent(context, AddEditActivity.class);
        } else if (location == 3) {
            intent1 = new Intent(context, ListActivity.class);
        } else {
            intent1 = new Intent(context, MainActivity.class);
        }
        context.startActivity(intent1);
    }
}
