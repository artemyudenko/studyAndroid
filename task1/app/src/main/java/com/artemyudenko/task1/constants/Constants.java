package com.artemyudenko.task1.constants;

public enum Constants {
    PREFERENCES_VALUE_KEY("preferences_text"), PREFERENCE_COLOR_KEY("color_text"),
    EDIT("Edit"), DELETE("Delete"), CONTEXT_MENU_HEADER("Select the action"),
    EDIT_NAME_KEY("edit_name_key"), EDIT_PRICE_KEY("edit_price_key"), EDIT_QUANTITY_KEY("edit_quantity_key"),
    EDIT_CHECKED_KEY("edit_checked_key"), EDIT_SUCCESS("edit_success");

    private String key;

    Constants(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
