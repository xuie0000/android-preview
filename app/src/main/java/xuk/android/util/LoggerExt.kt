package xuk.android.util

import timber.log.Timber

typealias Supplier<T> = () -> T

fun initLogger(isDebug: Boolean = true) {
  if (isDebug)
    Timber.plant(Timber.DebugTree())
  else
    Timber.plant(CrashReportingTree())

  log { "initLogger successfully, isDebug = $isDebug" }
}

inline fun log(supplier: Supplier<String>) = logd(supplier)

inline fun logd(supplier: Supplier<String>) = Timber.d(supplier())

inline fun logi(supplier: Supplier<String>) = Timber.i(supplier())

inline fun logw(supplier: Supplier<String>) = Timber.w(supplier())

inline fun loge(supplier: Supplier<String>) = Timber.e(supplier())