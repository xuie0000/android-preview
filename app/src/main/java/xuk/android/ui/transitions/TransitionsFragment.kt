package xuk.android.ui.transitions

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import xuk.android.R
import xuk.android.databinding.FragmentTransitionsBinding
import xuk.android.ui.renderscript.RenderScriptActivity

class TransitionsFragment : Fragment(R.layout.fragment_transitions) {
  private val binding: FragmentTransitionsBinding by viewBinding()

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.makeSceneTransitionAnimation.setOnClickListener {
      val intent = Intent(activity, SceneTransitionsActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(
        activity,
        binding.makeSceneTransitionAnimation,
        "share01"
      )
      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    binding.fabButton.setOnClickListener {
      Timber.d("fab_button")
      val intent = Intent(activity, SceneTransitionsActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(
        activity,
        // 创建多个共享元素
        Pair.create(binding.makeSceneTransitionAnimation, "share01"),
        Pair.create(binding.fabButton, "share02")
      )

      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    binding.explode.setOnClickListener { makeSceneTransitionAnimationNoParameter(0) }
    binding.slide.setOnClickListener { makeSceneTransitionAnimationNoParameter(1) }
    binding.fade.setOnClickListener { makeSceneTransitionAnimationNoParameter(2) }

    // fragment scene transition object
    binding.btnFragment.setOnClickListener {
      val extras = FragmentNavigatorExtras(
        binding.btnFragment to binding.btnFragment.transitionName,
        binding.ivShareObject to binding.ivShareObject.transitionName
      )
      findNavController().navigate(R.id.nav_transitions_object, null, null, extras)
    }


    binding.ivShareObject.setOnClickListener {
      Timber.d("share object")
      val intent = Intent(activity, RenderScriptActivity::class.java)
      val options =
        ActivityOptions.makeSceneTransitionAnimation(
          activity,
          binding.ivShareObject,
          "share_object"
        )
      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    val data = listOf(R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four)
    val listAdapter = TransitionsAdapter { v, resId, pos ->
      val bundle = bundleOf("resId" to resId)
      Timber.d("click pos $pos")
      val extras = FragmentNavigatorExtras(
        v to v.transitionName,
        binding.ivShareObject to binding.ivShareObject.transitionName
      )
      findNavController().navigate(R.id.nav_transitions_object, bundle, null, extras)
    }
    listAdapter.submitList(data)
    // When user hits back button transition takes backward
    postponeEnterTransition()
    binding.recyclerView.adapter = listAdapter
    binding.recyclerView.layoutManager =
      LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    binding.recyclerView.doOnPreDraw {
      startPostponedEnterTransition()
    }
  }

  private fun makeSceneTransitionAnimationNoParameter(flag: Int) {
    val intent = Intent(activity, SceneTransitionsActivity::class.java)
    intent.putExtra("flag", flag)
    val options = ActivityOptions.makeSceneTransitionAnimation(activity)
    ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
  }


}
