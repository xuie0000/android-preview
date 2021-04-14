package xuk.android.ui.main

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_transitions_object.*
import xuk.android.R
import java.util.concurrent.TimeUnit

class TransitionsObjectFragment : Fragment(R.layout.fragment_transitions_object) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // fragment shared object animation
    sharedElementEnterTransition = ChangeBounds().apply { duration = 300 }
    sharedElementReturnTransition = ChangeBounds().apply { duration = 300 }

    // recyclerview shared object animation
    sharedElementEnterTransition =
      TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    sharedElementReturnTransition =
      TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    postponeEnterTransition(300, TimeUnit.MILLISECONDS)

    super.onViewCreated(view, savedInstanceState)

    arguments?.getInt("resId")?.let {
      iv_res.setImageResource(it)
      iv_res.transitionName = it.toString()
    }
  }

}
