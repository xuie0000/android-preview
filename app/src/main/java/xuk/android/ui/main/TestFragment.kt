package xuk.android.ui.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import xuk.android.R
import xuk.android.databinding.FragmentTestBinding
import xuk.android.widget.TopicThumbnailDrawable

class TestFragment : Fragment(R.layout.fragment_test) {

  private val binding: FragmentTestBinding by viewBinding()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    shakeSample()
    selectDrawableSample()
  }

  private fun shakeSample() {
    binding.shakeView.setOnClickListener {
      it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake))
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

    binding.ivForeground.setImageDrawable(drawable)
    binding.ivForeground.setOnClickListener {
      it.isActivated = !it.isActivated
    }
  }


}
