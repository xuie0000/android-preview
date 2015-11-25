package com.xuie.androiddemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

    private final static String DATA = "ACCESSTOKEN";
    public final static String NOACESSTOKEN = "0001";

    public static void putAccesToken(Context context, String accessToken) {
        SharedPreferences share = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(DATA, accessToken);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DATA, NOACESSTOKEN);
    }


    public static void removeAccessToken(Context context) {
        SharedPreferences share = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(DATA, NOACESSTOKEN);
        editor.apply();
    }

}
