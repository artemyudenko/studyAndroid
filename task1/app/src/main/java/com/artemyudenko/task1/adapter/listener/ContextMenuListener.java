package com.artemyudenko.task1.adapter.listener;

import android.view.ContextMenu;
import android.view.View;

import com.artemyudenko.task1.constants.Constants;

public class ContextMenuListener implements View.OnCreateContextMenuListener {

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(Constants.CONTEXT_MENU_HEADER.getKey());
        menu.add(0, v.getId(), 0, Constants.DELETE.getKey());
    }
}
