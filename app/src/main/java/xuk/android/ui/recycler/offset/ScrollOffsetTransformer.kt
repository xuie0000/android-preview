package xuk.android.ui.recycler.offset

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import xuk.android.util.dp

/**
 * https://blog.csdn.net/XieYupeng520/article/details/49003613
 */
class ScrollOffsetTransformer : ViewPager2.PageTransformer {

  override fun transformPage(page: View, position: Float) {
    page.translationX = (-48).dp * position
  }
}
