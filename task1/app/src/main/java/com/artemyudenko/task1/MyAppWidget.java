package com.artemyudenko.task1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.RemoteViews;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    private static final String SHOW_IMAGES = "showImages";
    private static final String PLAY = "PLAY";
    private static final String STOP = "STOP";
    private static final String NEXT = "NEXT";
    private static final String PAUSE = "PAUSE";
    private static final String SHOW_LIST = "showList";

    private static SharedPreferences sharedPreferences;
    private static MediaPlayer mediaPlayer;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.browser, pendingIntent);

        Intent images = new Intent(context, MyAppWidget.class);
        images.setAction(SHOW_IMAGES);
        PendingIntent pendingIntentImages = PendingIntent.getBroadcast(context, 0, images, 0);
        views.setOnClickPendingIntent(R.id.change, pendingIntentImages);

        views.setImageViewResource(R.id.imageView3, R.raw.cat);

        Intent play = new Intent(context, MyAppWidget.class);
        play.setAction(PLAY);
        PendingIntent pendingIntentMusic = PendingIntent.getBroadcast(context, 0, play, 0);
        views.setOnClickPendingIntent(R.id.play, pendingIntentMusic);

        Intent pause = new Intent(context, MyAppWidget.class);
        pause.setAction(PAUSE);
        PendingIntent pendingIntentPause = PendingIntent.getBroadcast(context, 0, pause, 0);
        views.setOnClickPendingIntent(R.id.pause, pendingIntentPause);

        Intent stop = new Intent(context, MyAppWidget.class);
        stop.setAction(STOP);
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(context, 0, stop, 0);
        views.setOnClickPendingIntent(R.id.stop, pendingIntentStop);

        Intent next = new Intent(context, MyAppWidget.class);
        next.setAction(NEXT);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context, 0, next, 0);
        views.setOnClickPendingIntent(R.id.next, pendingIntentNext);

//        Intent list = new Intent(context, MyAppWidget.class);
//        list.setAction(SHOW_LIST);
//        PendingIntent pendingIntentList = PendingIntent.getBroadcast(context, 0, list, 0);
//        views.setOnClickPendingIntent(R.id.images, pendingIntentList);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        if (SHOW_IMAGES.equals(intent.getAction())) {
            boolean typeOfPicture = sharedPreferences.getBoolean("typeOfPicture", false);
            SharedPreferences.Editor edit = sharedPreferences.edit();

            if (typeOfPicture) {
                views.setImageViewResource(R.id.imageView3, R.raw.cat);
                edit.putBoolean("typeOfPicture", false);
            } else {
                views.setImageViewResource(R.id.imageView3, R.raw.dog);
                edit.putBoolean("typeOfPicture", true);
            }
            edit.apply();
        } else if (PLAY.equals(intent.getAction())) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.m1);
            }
            mediaPlayer.start();
        }  else if (PAUSE.equals(intent.getAction())) {
            mediaPlayer.pause();
        }  else if (STOP.equals(intent.getAction())) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }  else if (NEXT.equals(intent.getAction())) {
            boolean typeOfMedia = sharedPreferences.getBoolean("typeOfMedia", false);
            SharedPreferences.Editor edit = sharedPreferences.edit();

            if (typeOfMedia) {
                nextSong(true, context);
                edit.putBoolean("typeOfMedia", false);
            } else {
                nextSong(false, context);
                edit.putBoolean("typeOfMedia", true);
            }

            edit.apply();
        }
        appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidget.class), views);
    }

    private void nextSong(boolean song1, Context context) {
        mediaPlayer.reset();
        AssetFileDescriptor assetFileDescriptor;
        if (song1) {
            assetFileDescriptor = context.getResources().openRawResourceFd(R.raw.m1);
        } else {
            assetFileDescriptor = context.getResources().openRawResourceFd(R.raw.m2);
        }
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
    }


}

