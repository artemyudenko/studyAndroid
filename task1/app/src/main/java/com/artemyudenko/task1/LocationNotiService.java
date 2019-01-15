package com.artemyudenko.task1;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.artemyudenko.task1.db.DBEnum;
import com.artemyudenko.task1.db.DBManager;
import com.artemyudenko.task1.db.DBManagerLocal;
import com.artemyudenko.task1.model.Item;

import java.util.ArrayList;
import java.util.List;

public class LocationNotiService extends Service {

    public static final String CHANNEL_ID = "myChannelId";
    public static final String CHANNEL_NAME = "myChannelName";

    private DBManager dbManager;

    @Override
    public void onCreate() {
        dbManager = new DBManagerLocal(getApplicationContext());
        LocationListener locationListener = new LocationListener() {

            private Item item = null;

            @Override
            public void onLocationChanged(Location location) {
                dbManager.open();
                Cursor data = (Cursor) dbManager.fetch();
                dbManager.close();
                List<Item> items = new ArrayList<>();
                if (data.moveToFirst()) {
                    do {
                        items.add( new Item(
                                data.getString(data.getColumnIndex(DBEnum.NAME_COLUMN.getS())),
                                data.getString(data.getColumnIndex(DBEnum.LATITUDE_COLUMN.getS())),
                                data.getString(data.getColumnIndex(DBEnum.LENGTH_COLUMN.getS()))
                        ));
                    } while (data.moveToNext());
                }
                for (Item i : items) {
                    Location shop = new Location("shop");
                    shop.setLatitude(Double.parseDouble(i.getLattitude()));
                    shop.setLongitude(Double.parseDouble(i.getLength()));

                    Location current = new Location("current");
                    current.setLatitude(location.getLatitude());
                    current.setLongitude(location.getLongitude());

                    float dist = current.distanceTo(shop);

                    createNotificationChannel(getApplicationContext());
                    if (dist > 50.0 && i.equals(item)) {
                        showNotification(getApplicationContext(), "Goodbye", "We will miss you in " + i.getName());
                        item = null;
                    } else if (dist < 50.0) {
                        item = i;
                        showNotification(getApplicationContext(), "Hello", "Hello in " + i.getName());
                    }
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, locationListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(Context context, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, mBuilder.build());
    }

    private void createNotificationChannel(Context context) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription("despacito");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}