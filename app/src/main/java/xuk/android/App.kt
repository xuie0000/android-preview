package xuk.android

import android.app.Application
import com.gw.swipeback.tools.WxSwipeBackActivityManager
import com.tencent.bugly.crashreport.CrashReport
import xuk.android.util.initLogger
import xuk.android.util.log

/**
 * @author Jie Xu
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    log { "onCreate" }
    context = this
    initLogger(true)
    CrashReport.initCrashReport(applicationContext, BUG_LY_ID, false)

    WxSwipeBackActivityManager.getInstance().init(this)
  }

  companion object {
    lateinit var context: App
      private set

    private const val BUG_LY_ID = "bc342fcfab"
  }
}
