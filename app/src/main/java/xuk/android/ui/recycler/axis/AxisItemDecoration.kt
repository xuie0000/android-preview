package xuk.android.ui.recycler.axis

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

/**
 * http://www.jianshu.com/p/9a796bb23a47
 */
class AxisItemDecoration : RecyclerView.ItemDecoration() {
  private val linePaint: Paint = Paint()
  private val leftPaint: Paint = Paint()

  init {
    linePaint.color = Color.GREEN
    linePaint.strokeWidth = 6f

    leftPaint.color = Color.BLUE
    leftPaint.strokeWidth = 4f
    leftPaint.style = Paint.Style.FILL
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(c, parent, state)
    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val view = parent.getChildAt(i)
      val left = view.left
      val right = view.right
      val bottom = view.bottom
      if (i == childCount - 1)
        break
      c.drawRect((left + 30).toFloat(), bottom.toFloat(), (right - 30).toFloat(), (bottom + OUT_BOTTOM_HEIGHT).toFloat(), linePaint)
    }
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)
    // 获取Item的总数
    val childCount = parent.childCount
    // 遍历Item
    for (i in 0 until childCount) {
      // 获取每个Item的位置
      val view = parent.getChildAt(i)
      val index = parent.getChildAdapterPosition(view)

      // 左右上下的位置
      val left = view.left
      val right = view.right
      val top = view.top
      val bottom = view.bottom

      // 记录中心位置 (宽1/5，高1/2位置）
      val x = left + (right - left) / 5
      val y = top + (bottom - top) / 2

      // 左侧小圆点
      c.drawCircle(left.toFloat(), (top + bottom) / 2f, 10f, leftPaint)

      // 中线
      when (index) {
        0 -> {
          // 第一个
          c.drawLine(x.toFloat(), y.toFloat(), x.toFloat(), bottom.toFloat(), linePaint)
          c.drawCircle(x.toFloat(), y.toFloat(), 16f, linePaint)
        }
        childCount - 1 -> {
          // 最后一个
          c.drawLine(x.toFloat(), top.toFloat(), x.toFloat(), y.toFloat(), linePaint)
          c.drawCircle(x.toFloat(), y.toFloat(), 16f, linePaint)
        }
        else -> {
          c.drawLine(x.toFloat(), top.toFloat(), x.toFloat(), bottom.toFloat(), linePaint)
          c.drawCircle(x.toFloat(), y.toFloat(), 16f, linePaint)
        }
      }

    }

  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//    default(0, 0, 0, 0)
//    outRect.set(0, 0, 0, OUT_BOTTOM_HEIGHT)
    outRect.left = 20
    outRect.bottom = OUT_BOTTOM_HEIGHT
  }

  companion object {
    private const val OUT_BOTTOM_HEIGHT = 2
  }
}
