package com.teambition.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: dongwq
 * Date: 9/22/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrefUtil {
    public final static String SHARED_PREFERENCE = "preference_sett1ngs";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private PrefUtil(Context context) {
        preferences = context.getSharedPreferences(PrefUtil.SHARED_PREFERENCE, 0);
        editor = preferences.edit();
    }

    public static PrefUtil make(Context context) {
        return new PrefUtil(context);
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public PrefUtil putString(String key, String value) {
        editor.putString(key, value).commit();
        return this;
    }

    public PrefUtil putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value).commit();
        return this;
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public PrefUtil putInt(String key, int i) {
        editor.putInt(key, i).commit();
        return this;
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public Boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }


}
