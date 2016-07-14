package com.xuie.androiddemo.util;

import android.content.Context;

public class Utils {

    private final static String DATA = "ACCESSTOKEN";
    public final static String NOACESSTOKEN = "0001";

    public static void putAccessToken(Context context, String accessToken) {
        PreferenceUtils.setPref(context, DATA, accessToken);
    }

    public static String getAccessToken(Context context) {
        return PreferenceUtils.getPrefString(context, DATA, NOACESSTOKEN);
    }

    public static void removeAccessToken(Context context) {
        PreferenceUtils.setPref(context, DATA, NOACESSTOKEN);
    }

}
