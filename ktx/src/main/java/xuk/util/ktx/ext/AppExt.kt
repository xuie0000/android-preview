package xuk.util.ktx.ext

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import xuk.util.ktx.bean.AppInfo
import java.io.File

/**
 * Created by luyao
 * on 2019/6/12 10:53
 */

val Context.versionName: String
  get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode: Long
  get() = with(packageManager.getPackageInfo(packageName, 0)) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) longVersionCode else versionCode.toLong()
  }

fun Context.getAppInfo(apkPath: String): AppInfo? {
  packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA)?.let {
    it.applicationInfo.sourceDir = apkPath
    it.applicationInfo.publicSourceDir = apkPath

    val packageName = it.packageName
    val appName = packageManager.getApplicationLabel(it.applicationInfo).toString()
    val versionName = it.versionName
    val versionCode = it.versionCode
    val icon = packageManager.getApplicationIcon(it.applicationInfo)
    return AppInfo(apkPath, packageName, versionName, versionCode.toLong(), appName, icon)
  }
  return null
}

fun Context.getAppInfos(apkFolderPath: String): List<AppInfo?> {
  val appInfoList = ArrayList<AppInfo?>()
  for (file in File(apkFolderPath).listFiles())
    appInfoList.add(getAppInfo(file.path))
  return appInfoList
}

fun Context.getAppSignature(packageName: String = this.packageName): ByteArray? {
  val packageInfo: PackageInfo =
      packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
  val signatures = packageInfo.signatures
  return signatures[0].toByteArray()
}


