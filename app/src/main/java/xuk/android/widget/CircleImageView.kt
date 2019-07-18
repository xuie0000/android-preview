package xuk.android.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.NinePatchDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author xuie
 */
class CircleImageView : AppCompatImageView {
  private val mBorderThickness = 0
  private var mContext: Context? = null
  private val defaultColor = -0x1
  private val mBorderOutsideColor = 0
  private val mBorderInsideColor = 0
  private var defaultWidth = 0
  private var defaultHeight = 0

  constructor(context: Context) : super(context) {
    mContext = context
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    mContext = context
  }

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    mContext = context
  }

  override fun onDraw(canvas: Canvas) {
    val drawable = drawable ?: return

    if (width == 0 || height == 0) {
      return
    }
    this.measure(0, 0)
    if (drawable.javaClass == NinePatchDrawable::class.java) {
      return
    }
    val b = (drawable as BitmapDrawable).bitmap
    val bitmap = b.copy(Bitmap.Config.ARGB_8888, true)
    if (defaultWidth == 0) {
      defaultWidth = width

    }
    if (defaultHeight == 0) {
      defaultHeight = height
    }

    var radius = 0
    if (mBorderInsideColor != defaultColor && mBorderOutsideColor != defaultColor) {
      radius = (if (defaultWidth < defaultHeight) defaultWidth else defaultHeight) / 2 - 2 * mBorderThickness
      drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderInsideColor)
      drawCircleBorder(canvas, radius + mBorderThickness + mBorderThickness / 2, mBorderOutsideColor)
    } else if (mBorderInsideColor != defaultColor && mBorderOutsideColor == defaultColor) {
      radius = (if (defaultWidth < defaultHeight) defaultWidth else defaultHeight) / 2 - mBorderThickness
      drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderInsideColor)
    } else if (mBorderInsideColor == defaultColor && mBorderOutsideColor != defaultColor) {
      radius = (if (defaultWidth < defaultHeight) defaultWidth else defaultHeight) / 2 - mBorderThickness
      drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderOutsideColor)
    } else {
      radius = (if (defaultWidth < defaultHeight) defaultWidth else defaultHeight) / 2
    }
    val roundBitmap = getCroppedRoundBitmap(bitmap, radius)
    canvas.drawBitmap(roundBitmap, (defaultWidth / 2 - radius).toFloat(), (defaultHeight / 2 - radius).toFloat(), null)
  }

  private fun getCroppedRoundBitmap(bmp: Bitmap, radius: Int): Bitmap {
    val scaledSrcBmp: Bitmap
    val diameter = radius * 2
    val bmpWidth = bmp.width
    val bmpHeight = bmp.height
    var squareWidth = 0
    var squareHeight = 0
    var x = 0
    var y = 0
    val squareBitmap: Bitmap
    if (bmpHeight > bmpWidth) {
      squareHeight = bmpWidth
      squareWidth = squareHeight
      x = 0
      y = (bmpHeight - bmpWidth) / 2
      squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight)
    } else if (bmpHeight < bmpWidth) {
      squareHeight = bmpHeight
      squareWidth = squareHeight
      x = (bmpWidth - bmpHeight) / 2
      y = 0
      squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight)
    } else {
      squareBitmap = bmp
    }

    if (squareBitmap.width != diameter || squareBitmap.height != diameter) {
      scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true)

    } else {
      scaledSrcBmp = squareBitmap
    }
    val output = Bitmap.createBitmap(scaledSrcBmp.width, scaledSrcBmp.height,
        Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val paint = Paint()
    val rect = Rect(0, 0, scaledSrcBmp.width, scaledSrcBmp.height)

    paint.isAntiAlias = true
    paint.isFilterBitmap = true
    paint.isDither = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle((scaledSrcBmp.width / 2).toFloat(), (scaledSrcBmp.height / 2).toFloat(),
        (scaledSrcBmp.width / 2).toFloat(), paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(scaledSrcBmp, rect, rect, paint)
    return output
  }

  private fun drawCircleBorder(canvas: Canvas, radius: Int, color: Int) {
    val paint = Paint()
    paint.isAntiAlias = true
    paint.isFilterBitmap = true
    paint.isDither = true
    paint.color = color
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = mBorderThickness.toFloat()
    canvas.drawCircle((defaultWidth / 2).toFloat(), (defaultHeight / 2).toFloat(), radius.toFloat(), paint)
  }

}