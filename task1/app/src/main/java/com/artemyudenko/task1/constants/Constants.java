package com.artemyudenko.task1.constants;

public enum Constants {
    PREFERENCES_VALUE_KEY("preferences_text"), PREFERENCE_COLOR_KEY("color_text"),
    EDIT("edit"), DELETE("Delete"), CONTEXT_MENU_HEADER("Select the action");

    private String key;

    Constants(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
