package com.artemyudenko.task1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemyudenko.task1.R;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class ItemHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView description;
    private TextView branch;
    private EditText location;

    private LinearLayout linearLayout;

    ItemHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.name);
        this.description = itemView.findViewById(R.id.descriptionAdd);
        this.branch = itemView.findViewById(R.id.branch);
        this.location = itemView.findViewById(R.id.location);
        this.linearLayout = itemView.findViewById(R.id.valueLay);
    }
}
