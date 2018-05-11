package com.xuie.android;


import android.app.Application;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;

import com.gw.swipeback.tools.WxSwipeBackActivityManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuie.android.provider.ColorInitTask;
import com.xuie.android.ui.recycler.discrete.DiscreteScrollViewOptions;
import com.xuie.android.util.PreferenceUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author xuie
 */
public class App extends Application {
    private static App context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        context = this;
        PreferenceUtils.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "bc342fcfab", false);

        if (getNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Logger.d("onCreate");

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        // 永不退出
//        Cockroach.install((thread, throwable) -> new Handler(Looper.getMainLooper()).post(() -> {
//            try {
//                Log.d("Cockroach", thread + "\n" + throwable.toString());
//                throwable.printStackTrace();
//                Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//            } catch (Throwable e) {
//                Log.d("App", e.getMessage());
//            }
//        }));

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
