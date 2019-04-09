package com.xuie.android.ui.adapter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.xuie.android.R

/**
 * 感谢http://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview/27037230#27037230
 *
 * @author xuie
 */
class DividerDecoration : RecyclerView.ItemDecoration {

  private var context: Context? = null
  private var mDivider: Drawable? = null

  constructor(context: Context) {
    this.context = context

    var styledAttributes: TypedArray? = null
    try {
      styledAttributes = context.theme.obtainStyledAttributes(ATTRS)
      mDivider = styledAttributes!!.getDrawable(0)
    } finally {
      styledAttributes?.recycle()
    }
  }

  constructor(context: Context, resId: Int) {
    this.context = context
    mDivider = ContextCompat.getDrawable(context, resId)
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

    val left = parent.paddingLeft + context!!.resources.getDimensionPixelOffset(R.dimen.material_24dp)
    val right = parent.width - parent.paddingRight - context!!.resources.getDimensionPixelOffset(R.dimen.material_24dp)

    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)

      val params = child.layoutParams as RecyclerView.LayoutParams

      val top = child.bottom + params.bottomMargin
      val bottom = top + mDivider!!.intrinsicHeight

      mDivider!!.setBounds(left, top, right, bottom)
      mDivider!!.draw(c)
    }
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    outRect.set(0, 0, 0, 0)
  }

  companion object {

    private val ATTRS = intArrayOf(android.R.attr.listDivider)
  }
}