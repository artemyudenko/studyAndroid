package com.artemyudenko.task1.adapter.listener;

import android.view.View;
import android.widget.Toast;

public class ItemLongClickListener implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {
        Toast toast = Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT);
        toast.show();
        return false;
    }

}
