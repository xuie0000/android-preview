package xuk.android.ui.recycler

import androidx.navigation.fragment.findNavController
import xuk.android.R

class RecyclerViewFragment : RecyclerStringViewFragment() {

  private val members: List<String> = arrayListOf(
      "ItemDecoration",
      "Paging3",
      "GridLayout"
  )

  override fun loadData(): List<String> {
    return members
  }

  override fun loadClick(): (pos: Int) -> Unit {
    return { pos ->
      when (pos) {
        0 -> findNavController().navigate(R.id.action_to_axis)
        1 -> findNavController().navigate(R.id.action_to_paging)
        2 -> findNavController().navigate(R.id.action_to_grid_page)
        else -> throw IllegalArgumentException("no position")
      }
    }
  }

}
