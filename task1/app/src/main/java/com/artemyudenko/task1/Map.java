package com.artemyudenko.task1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artemyudenko.task1.db.DBEnum;
import com.artemyudenko.task1.db.DBManager;
import com.artemyudenko.task1.db.DBManagerLocal;
import com.artemyudenko.task1.model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity
        implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DBManager dbManagerLocal = new DBManagerLocal(this);
        dbManagerLocal.open();
        List<Item> items = new ArrayList<>();
        Cursor data = (Cursor) dbManagerLocal.fetch();
        if (data.moveToFirst()) {
            do {
                items.add( new Item(
                        data.getLong(data.getColumnIndex(DBEnum.ID_COLUMN.getS())),
                        data.getString(data.getColumnIndex(DBEnum.NAME_COLUMN.getS())),
                        data.getString(data.getColumnIndex(DBEnum.DESCRIPTION_COLUMN.getS())),
                        data.getString(data.getColumnIndex(DBEnum.BRANCH_COLUMN.getS())),
                        data.getString(data.getColumnIndex(DBEnum.LATITUDE_COLUMN.getS())),
                        data.getString(data.getColumnIndex(DBEnum.LENGTH_COLUMN.getS()))
                ));
            } while (data.moveToNext());
        }
        dbManagerLocal.close();

        for (Item i : items) {
            LatLng sydney = new LatLng(Double.parseDouble(i.getLattitude()), Double.parseDouble(i.getLength()));
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title(i.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
