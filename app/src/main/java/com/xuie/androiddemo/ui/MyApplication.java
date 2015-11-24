package com.xuie.androiddemo.ui;

import android.app.Application;

/**
 * Created by xuie on 15-11-10.
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
