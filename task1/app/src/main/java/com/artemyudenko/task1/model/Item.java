package com.artemyudenko.task1.model;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String price;
    private int quantity;
    private boolean checked;
}
