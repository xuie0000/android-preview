package xuk.android.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import timber.log.Timber
import xuk.android.R
import xuk.android.util.dp
import kotlin.math.min

class BottomQuadToClipView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

  private lateinit var src: Bitmap

  private val paint = Paint().apply {
    isFilterBitmap = false
    style = Paint.Style.FILL
  }
  private val xfermodePaint = Paint().apply {
    isFilterBitmap = false
    style = Paint.Style.FILL
    xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
  }

  private val path = Path()

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)

    path.moveTo(0f, 0f)
    path.lineTo(w.toFloat(), 0f)
    path.lineTo(w.toFloat(), (h - 32.dp).toFloat())
    path.quadTo((w / 2).toFloat(), h.toFloat() + 32.dp, 0f, (h - 32.dp).toFloat())
    path.close()

    src = makeSrc()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    // requested width and mode
    val reqWidth = MeasureSpec.getSize(widthMeasureSpec)
    val reqWidthMode = MeasureSpec.getMode(widthMeasureSpec)

    // requested height and mode
    val reqHeight = MeasureSpec.getSize(heightMeasureSpec)
    val reqHeightMode = MeasureSpec.getMode(heightMeasureSpec)

    // your choice
    val desiredWidth: Int = 300.dp // TODO("Define your desired width")
    val desiredHeight: Int = 300.dp// TODO("Define your desired height")

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


    Timber.d("width: $width, height: $height")
    Timber.d("reqWidthMode: $reqWidthMode, reqHeightMode: $reqHeightMode")

    // set the width and the height of the view
    setMeasuredDimension(width, height)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val sc = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
    canvas.drawPath(path, paint)
    canvas.drawBitmap(src, 0f, 0f, xfermodePaint)
    canvas.restoreToCount(sc)

  }

  // create a bitmap used for the "src" image
  private fun makeSrc(): Bitmap {
    return ContextCompat.getDrawable(context, R.mipmap.ic_share_image)!!.toBitmap(width, height)
  }

}