package com.artemyudenko.task1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemyudenko.task1.R;
import com.artemyudenko.task1.model.Item;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Item> itemss;

    public ListAdapter(List<Item> itemss) {
        this.itemss = itemss;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View elementOfList = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ItemHolder(elementOfList);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        Item item = itemss.get(i);
        itemHolder.getName().setText(item.getName());
        itemHolder.getPrice().setText(item.getPrice());
        itemHolder.getQuantity().setText(String.valueOf(item.getQuantity()));
        itemHolder.getChecked().setChecked(item.isChecked());
    }

    @Override
    public int getItemCount() {
        return itemss.size();
    }


}
