package xuk.android.ui.coordinator

import android.content.Intent
import xuk.android.ui.palette.PaletteActivity
import xuk.android.ui.recycler.RecyclerStringViewFragment

class CoordinatorSampleFragment : RecyclerStringViewFragment() {
  private val members: List<String> = arrayListOf(
      "coordinator sample",
      "motion layout sample 1",
      "motion layout sample 2",
      "palette tab layout",
  )

  override fun loadData(): List<String> {
    return members
  }

  override fun loadClick(): (pos: Int) -> Unit {
    return { pos ->
      when (pos) {
        0 -> startActivity(Intent(context, CoordinatorLayoutActivity::class.java))
        1 -> startActivity(Intent(context, MotionLayoutActivity::class.java))
        2 -> startActivity(Intent(context, MotionLayout2Activity::class.java))
        3 -> startActivity(Intent(context, PaletteActivity::class.java))
        else -> throw IllegalArgumentException("no position")
      }
    }
  }
}