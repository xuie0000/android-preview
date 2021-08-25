package xuk.android.ui.bottomsheet

import androidx.navigation.fragment.findNavController
import xuk.android.R
import xuk.android.ui.recycler.RecyclerStringViewFragment

class BottomSheetMainFragment : RecyclerStringViewFragment() {

  private val fullscreenFragment by lazy { BottomSheetFullscreenFragment() }

  private val members: List<String> = arrayListOf(
    "BottomSheetBehavior",
    "BottomSheetDialogFragment",
  )

  override fun loadData(): List<String> {
    return members
  }

  override fun loadClick(): (pos: Int) -> Unit {
    return { pos ->
      when (pos) {
        0 -> findNavController().navigate(R.id.bottom_sheet_behavior)
        1 -> fullscreenFragment.show(parentFragmentManager, "fullscreen")
        else -> throw IllegalArgumentException("no position")
      }
    }
  }

}
