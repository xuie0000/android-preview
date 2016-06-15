package com.xuie.androiddemo.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class MyActivityManager {

    private static MyActivityManager instance;
    private WeakReference<Activity> sCurrentActivity;

    private MyActivityManager() {
    }

    public final void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static MyActivityManager getInstance() {
        if (instance == null) {
            synchronized (MyActivityManager.class) {
                instance = new MyActivityManager();
            }
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivity != null) {
            currentActivity = sCurrentActivity.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivity = new WeakReference<>(activity);
    }

}
