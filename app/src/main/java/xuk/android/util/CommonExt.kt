package xuk.android.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import android.util.Base64
import android.view.PixelCopy
import android.view.View
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream


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

// https://www.itranslater.com/qa/details/2121697628307063808
@RequiresApi(Build.VERSION_CODES.O)
fun saveScreenshot(view: View, callback: ((bitmap: Bitmap) -> Unit)? = null, error: ((msg: String?) -> Unit)? = null) {
  val window = (view.context as Activity).window
  if (window != null) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val locationOfViewInWindow = IntArray(2)
    view.getLocationInWindow(locationOfViewInWindow)
    try {
      PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
        if (copyResult == PixelCopy.SUCCESS) {
          callback?.invoke(bitmap)
        }
        // possible to handle other result codes ...
      }, Handler())
    } catch (e: IllegalArgumentException) {
      // PixelCopy may throw IllegalArgumentException, make sure to handle it
      error?.invoke(e.message)
    }
  }
}

fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
  val bytes = ByteArrayOutputStream()
  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
  val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "share image", null)
  return Uri.parse(path.toString())
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