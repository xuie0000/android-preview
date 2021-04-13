package xuk.android.ui.main

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import androidx.fragment.app.Fragment
import xuk.android.R

class TransitionsObjectFragment : Fragment(R.layout.fragment_transitions_object) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    sharedElementEnterTransition = ChangeBounds().apply { duration = 300 }
    sharedElementReturnTransition= ChangeBounds().apply { duration = 300 }
    super.onViewCreated(view, savedInstanceState)
  }

}
