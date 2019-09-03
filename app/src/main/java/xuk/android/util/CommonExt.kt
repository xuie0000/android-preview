package xuk.android.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.View

/**
 * Created by luyao
 * on 2019/6/14 14:23
 */

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


val Context.screenWidth
  get() = resources.displayMetrics.widthPixels

val Context.screenHeight
  get() = resources.displayMetrics.heightPixels

fun Context.copyToClipboard(label: String, text: String) {
  val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clipData = ClipData.newPlainText(label, text)
  cm.primaryClip = clipData
}

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