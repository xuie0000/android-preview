package com.xuie.android;


import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xuie.android.util.PreferenceUtils;
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
        UMShareAPI.get(this);

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

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private int getNightMode() {
        return PreferenceUtils.getInt(this, "mode", AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static App getContext() {
        return context;
    }

    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
    }
}