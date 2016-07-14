package com.xuie.androiddemo.util;

import android.widget.Toast;

public class ToastUtils {

    public static void Toast(String string) {
        Toast(string, Toast.LENGTH_SHORT);
    }

    public static void Toast(String string, int dur) {
        Toast.makeText(MyActivityManager.getInstance().getCurrentActivity(), string, dur).show();
    }
}

