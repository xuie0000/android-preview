package com.xuie.androiddemo;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.util.MyActivityManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by xuie on 15-11-10.
 * MyApplication
 */
public class App extends Application {
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    static App context;

    @Override public void onCreate() {
        super.onCreate();
        context = this;

        RealmConfiguration configuration = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

        Logger.init();

        MyActivityManager.getInstance().init(this);
    }

    public static App getContext() {
        return context;
    }
}
