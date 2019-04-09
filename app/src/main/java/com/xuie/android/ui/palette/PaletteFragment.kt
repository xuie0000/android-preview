package com.xuie.android.ui.palette


import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.xuie.android.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author xuie
 */
class PaletteFragment : Fragment() {

  private var position: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    position = arguments!!.getInt(ARG_POSITION)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
        .MATCH_PARENT)

    val fl = FrameLayout(Objects.requireNonNull<FragmentActivity>(activity))
    fl.layoutParams = params
    fl.setBackgroundResource(drawables[position])
    val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f,
        resources.displayMetrics).toInt()

    val v = TextView(activity)
    params.setMargins(margin, margin, margin, margin)
    v.layoutParams = params
    v.layoutParams = params
    v.gravity = Gravity.BOTTOM
    v.text = "CARD " + (position + 1)

    fl.addView(v)
    return fl
  }

  companion object {
    private const val ARG_POSITION = "position"
    private val drawables = intArrayOf(R.mipmap.one, R.mipmap.two, R.mipmap.four, R.mipmap.three, R.mipmap.five)

    fun newInstance(position: Int): PaletteFragment {
      val f = PaletteFragment()
      val b = Bundle()
      b.putInt(ARG_POSITION, position)
      f.arguments = b
      return f
    }

    fun getBackgroundBitmapPosition(selectViewPagerItem: Int): Int {
      return drawables[selectViewPagerItem]
    }
  }

}
