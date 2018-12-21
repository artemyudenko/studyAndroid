package com.artemyudenko.task1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.artemyudenko.task1.db.DBManager;
import com.artemyudenko.task1.db.DBManagerLocal;
import com.artemyudenko.task1.model.Item;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class AddEditActivity extends AppCompatActivity {

    private DBManager dbManager;

    private TextView name;
    private TextView descriprion;
    private TextView branch;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        name = findViewById(R.id.nameAddEdit);
        descriprion = findViewById(R.id.descriptionAdd);
        branch = findViewById(R.id.branchAdd);

        dbManager = new DBManagerLocal(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    public void onAddEditSaveClick(View view) {
        CharSequence nameText = name.getText();
        if (TextUtils.isEmpty(nameText)) {
            name.setError("Name is required!");
        } else {
            CharSequence descriprionText = descriprion.getText();
            CharSequence branchText = branch.getText();
            Intent intent = new Intent(this, ListActivity.class);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Item item = constructItem(0, nameText.toString(), descriprionText.toString(),
                    branchText.toString());
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        item.setLattitude(String.valueOf(location.getLatitude()));
                        item.setLength(String.valueOf(location.getLongitude()));
                        dbManager.open();
                        dbManager.insert(item);
                        dbManager.close();
                        startActivity(intent);
                    });
        }
    }

    private Item constructItem(long id, String name, String description, String branch) {
        return new Item(id, name, description, branch);
    }
}
