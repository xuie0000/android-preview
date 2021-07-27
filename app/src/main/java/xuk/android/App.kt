package xuk.android

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport
import timber.log.Timber

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    CrashReport.initCrashReport(applicationContext, BUG_LY_ID, false)

  }

  companion object {
    private const val BUG_LY_ID = "bc342fcfab"
  }
}
