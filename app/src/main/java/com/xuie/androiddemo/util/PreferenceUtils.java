package com.xuie.androiddemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by xuie on 16-1-23.
 */
public class PreferenceUtils {
    public static SharedPreferences getPreference(Context con) {
        return PreferenceManager.getDefaultSharedPreferences(con);
    }

    public static boolean getBoolean(Context con, String key) {
        return getBoolean(con, key, false);
    }

    public static boolean getBoolean(Context con, String key, boolean def) {
        return getPreference(con).getBoolean(key, def);
    }

    public static int getInt(Context con, String key, int def) {
        return getPreference(con).getInt(key, def);
    }

    public static int getInt(Context con, String key) {
        return getPreference(con).getInt(key, 0);
    }

    public static String getString(Context con, String key) {
        return getString(con, key, null);
    }

    public static String getString(Context con, String key, String defString) {
        return getPreference(con).getString(key, defString);
    }

    public static void setPreference(Context con, String key, Object object) {
        SharedPreferences prefs = getPreference(con);
        SharedPreferences.Editor editor = prefs.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

}
