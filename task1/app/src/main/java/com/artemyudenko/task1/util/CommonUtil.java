package com.artemyudenko.task1.util;

import android.content.SharedPreferences;

import com.artemyudenko.task1.constants.Constants;

public final class CommonUtil {

    private CommonUtil(){}

    public static int getPreferencesSize(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(Constants.PREFERENCES_VALUE_KEY.getKey(),
                0);
    }

    public static String getPreferencesDropDown(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(Constants.PREFERENCE_COLOR_KEY.getKey(),
                "");
    }
}
