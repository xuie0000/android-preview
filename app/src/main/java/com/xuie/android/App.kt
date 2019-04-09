package com.xuie.android

import android.app.Application

import androidx.appcompat.app.AppCompatDelegate

import com.gw.swipeback.tools.WxSwipeBackActivityManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.crashreport.CrashReport
import com.xuie.android.provider.ColorInitTask
import com.xuie.android.ui.recycler.discrete.DiscreteScrollViewOptions
import com.xuie.android.util.PreferenceUtils

/**
 * The type App.
 *
 * @author xuie
 */
class App : Application() {

  private val isNightMode: Boolean
    get() = PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO) == AppCompatDelegate.MODE_NIGHT_YES

  private val isInitColorTask: Boolean
    get() = PreferenceUtils.getBoolean("color_init", false)

  override fun onCreate() {
    super.onCreate()
    Logger.addLogAdapter(AndroidLogAdapter())
    context = this
    PreferenceUtils.init(this)
    CrashReport.initCrashReport(applicationContext, "bc342fcfab", false)

    if (isNightMode) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    Logger.d("onCreate")

    if (!isInitColorTask) {
      ColorInitTask().execute()
    }

    DiscreteScrollViewOptions.init(this)

    WxSwipeBackActivityManager.getInstance().init(this)
  }

  companion object {
    /**
     * Gets context.
     *
     * @return the context
     */
    var context: App? = null
      private set
  }
}
