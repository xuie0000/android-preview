package com.xuie.android.ui.recycler.axis

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

/**
 * @author xuie
 * @date 17-8-9
 * http://www.jianshu.com/p/9a796bb23a47
 */

class AxisItemDecoration : RecyclerView.ItemDecoration() {
  private val mPaint: Paint = Paint()

  init {
    mPaint.color = Color.GREEN
    mPaint.strokeWidth = 6f
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
      c.drawRect((left + 30).toFloat(), bottom.toFloat(), (right - 30).toFloat(), (bottom + OUT_BOTTOM_HEIGHT).toFloat(), mPaint)
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

      if (index == 0) {
        c.drawLine(x.toFloat(), y.toFloat(), x.toFloat(), bottom.toFloat(), mPaint)
        c.drawCircle(x.toFloat(), y.toFloat(), 16f, mPaint)
      } else {
        c.drawLine(x.toFloat(), top.toFloat(), x.toFloat(), y.toFloat(), mPaint)
        c.drawLine(x.toFloat(), y.toFloat(), x.toFloat(), bottom.toFloat(), mPaint)
        c.drawCircle(x.toFloat(), y.toFloat(), 16f, mPaint)
      }
    }

  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    // default (0, 0, 0, 0)
    outRect.set(0, 0, 0, OUT_BOTTOM_HEIGHT)
  }

  companion object {
    private val OUT_BOTTOM_HEIGHT = 2
  }
}
