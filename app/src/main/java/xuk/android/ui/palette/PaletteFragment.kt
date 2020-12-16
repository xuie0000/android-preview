package xuk.android.ui.palette

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import xuk.android.R

class PaletteFragment : Fragment() {

  private var position: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    position = requireArguments().getInt(ARG_POSITION)
  }

  @SuppressLint("SetTextI18n")
  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f,
        resources.displayMetrics).toInt()

    val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
        .MATCH_PARENT).apply {
      setMargins(margin, margin, margin, margin)
    }

    val v = TextView(activity).apply {
      layoutParams = params
      gravity = Gravity.BOTTOM
      text = "CARD " + (position + 1)
    }

    return FrameLayout(requireActivity()).apply {
      layoutParams = params
      setBackgroundResource(drawables[position])
      addView(v)
    }
  }

  companion object {
    private const val ARG_POSITION = "position"
    private val drawables = intArrayOf(
        R.mipmap.one,
        R.mipmap.two,
        R.mipmap.four,
        R.mipmap.three,
        R.mipmap.five)

    fun newInstance(position: Int): PaletteFragment {
      return PaletteFragment().apply {
        arguments = Bundle().apply {
          putInt(ARG_POSITION, position)
        }
      }
    }

    fun getBackgroundBitmapPosition(selectViewPagerItem: Int): Int {
      return drawables[selectViewPagerItem]
    }
  }

}
