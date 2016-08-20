package com.xuie.androiddemo;



import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.util.MyActivityManager;
import com.xuie.androiddemo.util.PreferenceUtils;
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
    private static App context;

    @Override public void onCreate() {
        super.onCreate();
        Logger.init();
        context = this;

        System.out.println("mode:" + getNightMode());
        if (getNightMode() == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Logger.d("onCreate");

        RealmConfiguration configuration = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

        MyActivityManager.getInstance().init(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    @AppCompatDelegate.NightMode
    private int getNightMode() {
        return PreferenceUtils.getInt(this, "mode", AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static App getContext() {
        return context;
    }
}
