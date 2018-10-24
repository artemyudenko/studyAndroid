package com.artemyudenko.task1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private long id;
    private String name;
    private String price;
    private int quantity;
    private boolean checked;
}
