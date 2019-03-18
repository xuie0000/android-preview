package com.xuie.android;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.gw.swipeback.tools.WxSwipeBackActivityManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuie.android.provider.ColorInitTask;
import com.xuie.android.ui.recycler.discrete.DiscreteScrollViewOptions;
import com.xuie.android.util.PreferenceUtils;

/**
 * The type App.
 *
 * @author xuie
 */
public class App extends Application {
    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        context = this;
        PreferenceUtils.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "bc342fcfab", false);

        if (isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Logger.d("onCreate");

        if (!isInitColorTask()) {
            new ColorInitTask().execute();
        }

        DiscreteScrollViewOptions.init(this);

        WxSwipeBackActivityManager.getInstance().init(this);
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public static App getContext() {
        return context;
    }

    private boolean isNightMode() {
        return PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO) == AppCompatDelegate.MODE_NIGHT_YES;
    }

    private boolean isInitColorTask() {
        return PreferenceUtils.getBoolean("color_init", false);
    }
}
