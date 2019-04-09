package com.xuie.android.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.xuie.android.R

/**
 * 感谢http://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-
 * between-items-in-recyclerview/27037230#27037230
 *
 * @author xuie
 */
class DividerOverDecoration : RecyclerView.ItemDecoration {

  private var context: Context? = null
  private var mDivider: Drawable? = null

  constructor(context: Context) {
    this.context = context

    val typedValue = TypedValue()
    if (context.theme
            .resolveAttribute(android.R.attr.listDivider, typedValue, true) && typedValue.data != 0) {
      this.mDivider = context.resources
          .getDrawable(typedValue.resourceId)
    }
  }

  constructor(context: Context, resId: Int) {
    this.context = context
    mDivider = ContextCompat.getDrawable(context, resId)
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
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
}