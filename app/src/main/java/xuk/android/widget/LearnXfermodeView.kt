package xuk.android.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import xuk.android.util.dp
import kotlin.math.min


/**
 * https://android.googlesource.com/platform/development/+/master/samples/ApiDemos/src/com/example/android/apis/graphics/Xfermodes.java
 */
class LearnXfermodeView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

  private val W = 72.dp
  private val H = 72.dp
  private val ROW_MAX = 4 // number of samples per row

  private var mSrcB: Bitmap = makeSrc(W, H)
  private var mDstB: Bitmap = makeDst(W, H)

  // background checker-board pattern
  private var mBG: BitmapShader? = null

  private val sModes = arrayOf<Xfermode>(
    PorterDuffXfermode(PorterDuff.Mode.CLEAR),
    PorterDuffXfermode(PorterDuff.Mode.SRC),
    PorterDuffXfermode(PorterDuff.Mode.DST),
    PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
    PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
    PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
    PorterDuffXfermode(PorterDuff.Mode.DST_IN),
    PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
    PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
    PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
    PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
    PorterDuffXfermode(PorterDuff.Mode.XOR),
    PorterDuffXfermode(PorterDuff.Mode.DARKEN),
    PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
    PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
    PorterDuffXfermode(PorterDuff.Mode.SCREEN),
    PorterDuffXfermode(PorterDuff.Mode.ADD),
    PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
  )
  private val sLabels = arrayOf(
    "Clear", "Src", "Dst", "SrcOver",
    "DstOver", "SrcIn", "DstIn", "SrcOut",
    "DstOut", "SrcATop", "DstATop", "Xor",
    "Darken", "Lighten", "Multiply", "Screen",
    "Add", "Overlay"
  )

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    isFilterBitmap = false
  }

  private val labelP = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    textSize = 18.dp.toFloat()
    textAlign = Paint.Align.CENTER
  }

  init {

    // make a checkerboard pattern
    val bm = Bitmap.createBitmap(
      intArrayOf(
        0xFFFFFF, 0xCCCCCC,
        0xCCCCCC, 0xFFFFFF
      ), 2, 2,
      Bitmap.Config.RGB_565
    )
    mBG = BitmapShader(
      bm,
      Shader.TileMode.REPEAT,
      Shader.TileMode.REPEAT
    )
    val m = Matrix()
    m.setScale(6f, 6f)

    mBG?.setLocalMatrix(m)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    // requested width and mode
    val reqWidth = MeasureSpec.getSize(widthMeasureSpec)
    val reqWidthMode = MeasureSpec.getMode(widthMeasureSpec)

    // requested height and mode
    val reqHeight = MeasureSpec.getSize(heightMeasureSpec)
    val reqHeightMode = MeasureSpec.getMode(heightMeasureSpec)

    // your choice
    val desiredWidth: Int = 400.dp // TODO("Define your desired width")
    val desiredHeight: Int = 600.dp// TODO("Define your desired height")

    val width = when (reqWidthMode) {
      MeasureSpec.EXACTLY -> reqWidth
      MeasureSpec.UNSPECIFIED -> desiredWidth
      else -> min(reqWidth, desiredWidth) // AT_MOST condition
    }

    val height = when (reqHeightMode) {
      MeasureSpec.EXACTLY -> reqHeight
      MeasureSpec.UNSPECIFIED -> desiredHeight
      else -> min(reqHeight, desiredHeight) // AT_MOST condition
    }

    // set the width and the height of the view
    setMeasuredDimension(width, height)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    canvas.drawColor(Color.WHITE)
    canvas.translate(8.dp.toFloat(), 16.dp.toFloat() + labelP.textSize)

    var x = 0
    var y = 0
    for (i in sModes.indices) {
      // draw the border
      paint.style = Paint.Style.STROKE
      paint.shader = null
      canvas.drawRect(
        x - 0.5f, y - 0.5f,
        x + W + 0.5f, y + H + 0.5f, paint
      )
      // draw the checker-board pattern
      paint.style = Paint.Style.FILL
      paint.shader = mBG
      canvas.drawRect(x.toFloat(), y.toFloat(), (x + W).toFloat(), (y + H).toFloat(), paint)
      // draw the src/dst example into our offscreen bitmap
      val sc = canvas.saveLayer(
        x.toFloat(), y.toFloat(), (x + W).toFloat(),
        (y + H).toFloat(), null
      )

      canvas.translate(x.toFloat(), y.toFloat())
      canvas.drawBitmap(mDstB, 0f, 0f, paint)
      paint.xfermode = sModes[i]
      canvas.drawBitmap(mSrcB, 0f, 0f, paint)
      paint.xfermode = null
      canvas.restoreToCount(sc)
      // draw the label
      canvas.drawText(
        sLabels[i],
        (x + W / 2).toFloat(), y - labelP.textSize / 2, labelP
      )
      x += W + 8.dp
      // wrap around when we've drawn enough for one row
      if (i % ROW_MAX == ROW_MAX - 1) {
        x = 0
        y += H + 8.dp + labelP.textSize.toInt()
      }
    }
  }


  // create a bitmap with a circle, used for the "dst" image
  private fun makeDst(w: Int, h: Int): Bitmap {
    val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val c = Canvas(bm)
    val p = Paint(Paint.ANTI_ALIAS_FLAG)
    p.color = Color.parseColor("#FFFFCC44")
    c.drawOval(RectF(0f, 0f, (w * 3 / 4).toFloat(), (h * 3 / 4).toFloat()), p)
    return bm
  }

  // create a bitmap with a rect, used for the "src" image
  private fun makeSrc(w: Int, h: Int): Bitmap {
    val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val c = Canvas(bm)
    val p = Paint(Paint.ANTI_ALIAS_FLAG)
    p.color = Color.parseColor("#FF66AAFF")
    c.drawRect(
      (w / 3).toFloat(), (h / 3).toFloat(), (w * 19 / 20).toFloat(),
      (h * 19 / 20).toFloat(), p
    )
    return bm
  }

}