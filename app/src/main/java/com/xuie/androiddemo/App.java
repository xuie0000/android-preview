package com.xuie.androiddemo;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.util.MyActivityManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

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

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static App getContext() {
        return context;
    }
}
