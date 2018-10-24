package com.artemyudenko.task1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemyudenko.task1.R;
import com.artemyudenko.task1.adapter.listener.ContextMenuListener;
import com.artemyudenko.task1.model.Item;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ListAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Item> itemss;
    private @Getter @Setter int position;

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
    public void onBindViewHolder(@NonNull final ItemHolder itemHolder, int i) {
        Item item = itemss.get(i);
        itemHolder.getName().setText(item.getName());
        itemHolder.getPrice().setText(item.getPrice());
        itemHolder.getQuantity().setText(String.valueOf(item.getQuantity()));
        itemHolder.getChecked().setChecked(item.isChecked());
        ContextMenuListener contextMenuListener = new ContextMenuListener();
        itemHolder.getLinearLayout().setOnCreateContextMenuListener(contextMenuListener);
        itemHolder.getLinearLayout().setOnLongClickListener(v -> {
            setPosition(itemHolder.getLayoutPosition());
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return itemss.size();
    }


}
