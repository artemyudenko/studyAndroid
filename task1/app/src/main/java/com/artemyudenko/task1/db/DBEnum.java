package com.artemyudenko.task1.db;

public enum DBEnum {

    TABLE_NAME("ITEM_TABLE"),
    ID_COLUMN("id"),
    NAME_COLUMN("name"),
    PRICE_COLUMN("price"),
    QUANTITY_COLUMN("quantity"),
    CHECKED_COLUMN("checked"),
    ACTIVE_COLUMN("active");

    public String getS() {
        return s;
    }

    public static String[] getColumnNamesArray() {
        return new String[]{ID_COLUMN.s, NAME_COLUMN.s, PRICE_COLUMN.s,
                QUANTITY_COLUMN.s, CHECKED_COLUMN.s, ACTIVE_COLUMN.s};
    }

    DBEnum(String s) {
        this.s = s;
    }

    private String s;
}
