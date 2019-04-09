package com.xuie.android.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.Display

import java.lang.reflect.Field

/**
 * @author yf
 * @date 15-11-4
 */
object ScreenUtils {

  fun getPoint(activity: Activity): Point {
    val display = activity.windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
  }

  fun getWidth(activity: Activity): Int {
    return getPoint(activity).x
  }

  fun getHeight(activity: Activity): Int {
    return getPoint(activity).y
  }

  fun getStatusBarHeight(context: Context): Int {
    var c: Class<*>? = null
    var obj: Any? = null
    var field: Field? = null
    var x = 0
    var statusBarHeight = 0
    try {
      c = Class.forName("com.android.internal.R\$dimen")
      obj = c!!.newInstance()
      field = c.getField("status_bar_height")
      x = Integer.parseInt(field!!.get(obj).toString())
      statusBarHeight = context.resources.getDimensionPixelSize(x)
    } catch (e1: Exception) {
      e1.printStackTrace()
    }

    return statusBarHeight
  }

  fun getNavigationBarHeight(activity: Activity): Int {
    val resources = activity.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    //获取NavigationBar的高度
    return resources.getDimensionPixelSize(resourceId)
  }

  fun dpToPx(dp: Float): Float {
    return dp * Resources.getSystem().displayMetrics.density
  }

  fun pxToDp(px: Float): Float {
    return px / Resources.getSystem().displayMetrics.density
  }

  fun dpToPxInt(dp: Float): Float {
    return (dpToPx(dp) + 0.5f).toInt().toFloat()
  }

  fun pxToDpCeilInt(px: Float): Float {
    return (pxToDp(px) + 0.5f).toInt().toFloat()
  }

  fun getPixelFromDPI(dpi: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, Resources.getSystem().displayMetrics).toInt()
  }
}
