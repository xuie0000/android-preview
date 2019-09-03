package xuk.android

import android.app.Application
import com.gw.swipeback.tools.WxSwipeBackActivityManager
import com.tencent.bugly.crashreport.CrashReport
import xuk.android.provider.ColorInitTask
import xuk.android.ui.recycler.discrete.DiscreteScrollViewOptions
import xuk.android.util.getSpValue
import xuk.android.util.initLogger
import xuk.android.util.log

/**
 * The type App.
 *
 * @author Jie Xu
 */
class App : Application() {

  private val isInitColorTask: Boolean
    get() = getSpValue("color_init", false)

  override fun onCreate() {
    super.onCreate()
    context = this

    initLogger(true)


    CrashReport.initCrashReport(applicationContext, "bc342fcfab", false)

    log { "onCreate" }

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
    lateinit var context: App
      private set
  }
}
