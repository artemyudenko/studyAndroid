package com.artemyudenko.task1.constants;

public enum Constants {
    PREFERENCES_VALUE_KEY("preferences_text"), PREFERENCE_COLOR_KEY("color_text");

    private String key;

    Constants(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
