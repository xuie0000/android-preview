package xuk.android.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import xuk.android.util.dp

class BottomQuadToClipImageView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

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

  override fun onDraw(canvas: Canvas) {
//    super.onDraw(canvas)
    val sc = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
    canvas.drawPath(path, paint)
    canvas.drawBitmap(src, 0f, 0f, xfermodePaint)
    canvas.restoreToCount(sc)
  }

  // create a bitmap used for the "src" image
  private fun makeSrc(): Bitmap {
    return drawable.toBitmap(width, height)
  }

}