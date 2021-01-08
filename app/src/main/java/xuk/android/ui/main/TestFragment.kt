package xuk.android.ui.main

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.view.*
import xuk.android.R
import xuk.android.widget.TopicThumbnailDrawable

class TestFragment : Fragment(R.layout.fragment_test) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    shakeSample()

    selectDrawableSample()

  }

  private fun shakeSample() {
    shake_view.apply {
      setImageDrawable(IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_view_carousel).color(Color.BLUE).sizeDp(24))
      setOnClickListener {
        shake_view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake))
      }
    }
  }

  private fun selectDrawableSample() {
    val selectedTint = requireContext().getColor(R.color.select_tint)
    val selectedTopLeftCornerRadius = requireContext().resources
        .getDimensionPixelSize(R.dimen.small_component_top_left_radius)
    val selectedDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_checkmark)

    val drawable = TopicThumbnailDrawable(
        selectedTint,
        selectedTopLeftCornerRadius,
        selectedDrawable!!
    ).apply {
      bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_share_image)
    }

    iv_foreground.setImageDrawable(drawable)
    iv_foreground.setOnClickListener {
      it.isActivated = !it.isActivated
    }
  }


}
