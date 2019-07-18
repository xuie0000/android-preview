package xuk.android.ui.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Jie Xu
 */
class MarginDecoration : RecyclerView.ItemDecoration {
  private var margin: Int = 0

  constructor() {
    margin = (8 * Resources.getSystem().displayMetrics.density).toInt()
  }

  constructor(dp: Int) {
    margin = (dp * Resources.getSystem().displayMetrics.density).toInt()
  }


  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    outRect.set(margin, margin, margin, margin)
  }
}