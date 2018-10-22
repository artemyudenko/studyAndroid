package com.artemyudenko.task1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemyudenko.task1.R;
import com.artemyudenko.task1.adapter.listener.ContextMenuListener;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView price;
    private TextView quantity;
    private CheckBox checked;

    private LinearLayout linearLayout;

    ItemHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.name);
        this.price = itemView.findViewById(R.id.price);
        this.quantity = itemView.findViewById(R.id.numberOfProducts);
        this.checked = itemView.findViewById(R.id.checked);
        this.linearLayout = itemView.findViewById(R.id.valueLay);

        ContextMenuListener conextMenuListener = new ContextMenuListener();
        this.linearLayout.setOnCreateContextMenuListener(conextMenuListener);
    }
}
