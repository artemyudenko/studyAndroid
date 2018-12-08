package com.artemyudenko.task1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Item {
    private long id;
    private String name;
    private String price;
    private int quantity;
    private boolean checked;

    private String key;

    public Item(long id, String name, String price, int quantity, boolean checked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checked = checked;
    }
}
