package com.artemyudenko.task1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private long id;
    private String name;
    private String description;
    private String branch;
    private String lattitude;
    private String length;

    public Item(long id, String name, String description, String branch) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.branch = branch;
    }
}
