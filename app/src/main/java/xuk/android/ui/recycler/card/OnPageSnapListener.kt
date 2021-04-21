package xuk.android.ui.recycler.card

import androidx.viewpager2.widget.ViewPager2

class OnPageSnapListener(
  private val callback: (Int) -> Unit
) : ViewPager2.OnPageChangeCallback() {
  private var previousPosition: Int = -1

  override fun onPageSelected(position: Int) {
    super.onPageSelected(position)
    if (position != previousPosition) {
      callback(previousPosition)
    }
    previousPosition = position
  }
}