package com.artemyudenko.task1.db;

public enum DBEnum {

    DB_NAME("DB"),
    TABLE_NAME("ITEM_TABLE"),
    ID_COLUMN("id"),
    NAME_COLUMN("name"),
    DESCRIPTION_COLUMN("description"),
    BRANCH_COLUMN("branch"),
    LATITUDE_COLUMN("latitude"),
    LENGTH_COLUMN("length");

    public String getS() {
        return s;
    }

    public static String[] getColumnNamesArray() {
        return new String[]{ID_COLUMN.s, NAME_COLUMN.s, DESCRIPTION_COLUMN.s,
                BRANCH_COLUMN.s, LATITUDE_COLUMN.s, LENGTH_COLUMN.s};
    }

    DBEnum(String s) {
        this.s = s;
    }

    private String s;
}
