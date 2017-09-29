package com.xuie.android;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.gw.swipeback.tools.WxSwipeBackActivityManager;
import com.orhanobut.logger.Logger;
import com.xuie.android.exception.Cockroach;
import com.xuie.android.provider.ColorInitTask;
import com.xuie.android.ui.recyclerView.discrete.DiscreteScrollViewOptions;
import com.xuie.android.util.PreferenceUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by xuie on 15-11-10.
 * MyApplication
 */
public class App extends Application {
    private static App context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
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

        Cockroach.install((thread, throwable) -> new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Log.d("Cockroach", thread + "\n" + throwable.toString());
                throwable.printStackTrace();
                Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
            } catch (Throwable e) {
                Log.d("App", e.getMessage());
            }
        }));

        new ColorInitTask().execute();

        DiscreteScrollViewOptions.init(this);

        WxSwipeBackActivityManager.getInstance().init(this);
    }

    public static App getContext() {
        return context;
    }

    private int getNightMode() {
        return PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
