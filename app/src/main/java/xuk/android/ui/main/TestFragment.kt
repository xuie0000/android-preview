package xuk.android.ui.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber
import xuk.android.R
import xuk.android.databinding.FragmentTestBinding
import xuk.android.widget.TopicThumbnailDrawable

class TestFragment : Fragment(R.layout.fragment_test) {

  private val binding: FragmentTestBinding by viewBinding()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    shakeSample()
    selectDrawableSample()
    showDialogSample()
  }

  private fun showDialogSample() {
    binding.btnDialog.setOnClickListener {
      materialDialogTest()
    }
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

  private fun materialDialogTest() {
    val editText = EditText(requireContext())
    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(11))
    editText.hint = "hint"
    editText.setPadding(16, 16, 16, 16)

    val dialog = MaterialAlertDialogBuilder(requireContext())
      .setTitle("跳过？")
      .setView(editText)
      .setMessage("下次是否不再显示登录界面？")
      .setPositiveButton("YES") { _, _ ->
        Timber.d("YES")
      }
      .setNegativeButton("CANCEL") { _, _ ->
        Timber.d("Cancel")
      }
      .create()
    dialog.window!!.attributes.width = 400

    dialog.show()
  }

  private fun hideSoftInput() {
    // 隐藏对话框
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
  }


}
