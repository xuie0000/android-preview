package xuk.android.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import xuk.android.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 * @author Jie Xu
 */
class TestFragment : Fragment() {
  private var shakeView: ImageView? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_test, container, false)
    shakeView = view.findViewById(R.id.shake_view)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    shakeView!!.setImageDrawable(IconicsDrawable(Objects.requireNonNull<FragmentActivity>(activity), MaterialDesignIconic.Icon.gmi_view_carousel).color(Color.BLUE).sizeDp(24))
    shakeView!!.setOnClickListener { v -> shakeView!!.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake)) }
  }

}
