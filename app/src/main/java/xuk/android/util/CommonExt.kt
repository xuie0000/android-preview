package xuk.android.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.provider.MediaStore
import android.view.View

fun Context.dp2px(dp: Float): Int {
  val scale = resources.displayMetrics.density
  return (dp * scale + 0.5f).toInt()
}

fun Context.px2dp(px: Float): Int {
  val scale = resources.displayMetrics.density
  return (px / scale + 0.5f).toInt()
}

fun View.dp2px(dp: Float): Int {
  val scale = resources.displayMetrics.density
  return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Float): Int {
  val scale = resources.displayMetrics.density
  return (px / scale + 0.5f).toInt()
}

fun sp2px(spValue: Float): Int {
  val fontScale = Resources.getSystem().displayMetrics.scaledDensity
  return (spValue * fontScale + 0.5f).toInt()
}


val Context.screenWidth
  get() = resources.displayMetrics.widthPixels

val Context.screenHeight
  get() = resources.displayMetrics.heightPixels

fun Activity.screenShotToUri(): Uri {
  // https://medium.com/@shiveshmehta09/taking-screenshot-programmatically-using-pixelcopy-api-83c84643b02a
  val dView = window.decorView
  dView.isDrawingCacheEnabled = true
  dView.buildDrawingCache()
  val bmp = dView.drawingCache

  // 将Bitmap转换为Uri
  val pathOfBmp = MediaStore.Images.Media.insertImage(contentResolver, bmp, "title", null)
  val bmpUri = Uri.parse(pathOfBmp)

  // 清理截屏缓存
  dView.isDrawingCacheEnabled = false
  dView.destroyDrawingCache()

  return bmpUri
}