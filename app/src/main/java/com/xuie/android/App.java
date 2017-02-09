package com.xuie.android;


import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.logger.Logger;
import com.xuie.android.util.PreferenceUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by xuie on 15-11-10.
 * MyApplication
 */
public class App extends Application {
    private static App context;

    @Override public void onCreate() {
        super.onCreate();
        Logger.init();
        context = this;
        PreferenceUtils.init(this);

        if (getNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Logger.d("onCreate");

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
    }

    public static App getContext() {
        return context;
    }

    private int getNightMode() {
        return PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
    }
}
