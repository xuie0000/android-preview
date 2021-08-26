package xuk.android.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import xuk.android.R
import xuk.android.databinding.FragmentBottomSheetBehaviorBinding

class BottomSheetBehaviorFragment : Fragment() {

  private lateinit var binding: FragmentBottomSheetBehaviorBinding

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {

    binding = FragmentBottomSheetBehaviorBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<AppCompatButton>(R.id.btn_show).setOnClickListener {
      bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    val bottomSheet = view.findViewById<ConstraintLayout>(R.id.sheet_layout)
    bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
    bottomSheetBehavior.isHideable = true
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

    view.findViewById<AppCompatTextView>(R.id.tv_hide).setOnClickListener {
      if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
      } else {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
      }
    }
  }

}