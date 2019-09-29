package xuk.android.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import kotlinx.android.synthetic.main.fragment_test.*
import xuk.android.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 * @author Jie Xu
 */
class TestFragment : Fragment(R.layout.fragment_test) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    shake_view.setImageDrawable(IconicsDrawable(
        Objects.requireNonNull<FragmentActivity>(activity),
        MaterialDesignIconic.Icon.gmi_view_carousel).color(Color.BLUE).sizeDp(24)
    )
    shake_view.setOnClickListener {
      shake_view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake))
    }
  }

}
