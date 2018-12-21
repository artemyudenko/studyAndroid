package com.artemyudenko.task1.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemyudenko.task1.R;
import com.artemyudenko.task1.adapter.listener.ContextMenuListener;
import com.artemyudenko.task1.model.Item;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public class ListAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Item> itemss;
    private Context context;
    private @Getter @Setter int position;

    public ListAdapter(List<Item> itemss, Context context) {
        this.itemss = itemss;
        this.context = context;
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
        itemHolder.getDescription().setText(item.getDescription());
        itemHolder.getBranch().setText(String.valueOf(item.getBranch()));

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> listAddresses = null;
        try {
            listAddresses = geocoder.getFromLocation(Double.parseDouble(item.getLattitude()),Double.parseDouble( item.getLength()), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String location = listAddresses.get(0).getAddressLine(0);

        itemHolder.getLocation().setText(String.valueOf(location));
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
