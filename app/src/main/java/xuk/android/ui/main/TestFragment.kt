package xuk.android.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.view.*
import xuk.android.R
import xuk.android.widget.TopicThumbnailTarget

class TestFragment : Fragment(R.layout.fragment_test) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    shake_view.apply {
      setImageDrawable(IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_view_carousel).color(Color.BLUE).sizeDp(24))
      setOnClickListener {
        shake_view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake))
      }
    }

    val selectedTint = context!!.getColor(R.color.topic_tint)
    val selectedTopLeftCornerRadius =
        context!!.resources.getDimensionPixelSize(R.dimen.small_component_top_left_radius)
    val selectedDrawable = context!!.getDrawable(R.drawable.ic_checkmark)!!

    iv_foreground.setOnClickListener {
      it.isActivated = !it.isActivated
    }

    Glide.with(iv_foreground)
        .asBitmap()
        .load("android.resource://" + context!!.packageName + "/drawable/" + R.mipmap.image_small)
//        .load("https://source.unsplash.com/RFDP7_80v5A")
        .placeholder(R.drawable.course_image_placeholder)
        .into(
            TopicThumbnailTarget(
                iv_foreground,
                selectedTint,
                selectedTopLeftCornerRadius,
                selectedDrawable
            )
        )

  }


}
