package xuk.android.ui.transitions

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import by.kirich1409.viewbindingdelegate.viewBinding
import xuk.android.R
import xuk.android.databinding.FragmentTransitionsObjectBinding
import java.util.concurrent.TimeUnit

class TransitionsObjectFragment : Fragment(R.layout.fragment_transitions_object) {
  private val binding: FragmentTransitionsObjectBinding by viewBinding()

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
      binding.ivRes.setImageResource(it)
      binding.ivRes.transitionName = it.toString()
    }
  }

}
