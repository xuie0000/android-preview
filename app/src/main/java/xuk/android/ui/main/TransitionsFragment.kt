package xuk.android.ui.main

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
import kotlinx.android.synthetic.main.fragment_transitions.*
import timber.log.Timber
import xuk.android.R
import xuk.android.ui.renderscript.RenderScriptActivity

class TransitionsFragment : Fragment(R.layout.fragment_transitions) {

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    make_scene_transition_animation.setOnClickListener {
      val intent = Intent(activity, SceneTransitionsActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(
        activity,
        make_scene_transition_animation,
        "share01"
      )
      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    fab_button.setOnClickListener {
      Timber.d("fab_button")
      val intent = Intent(activity, SceneTransitionsActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(
        activity,
        // 创建多个共享元素
        Pair.create(make_scene_transition_animation, "share01"),
        Pair.create(fab_button, "share02")
      )

      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    explode.setOnClickListener { makeSceneTransitionAnimationNoParameter(0) }
    slide.setOnClickListener { makeSceneTransitionAnimationNoParameter(1) }
    fade.setOnClickListener { makeSceneTransitionAnimationNoParameter(2) }

    // fragment scene transition object
    btn_fragment.setOnClickListener {
      val extras = FragmentNavigatorExtras(
        btn_fragment to btn_fragment.transitionName,
        iv_share_object to iv_share_object.transitionName
      )
      findNavController().navigate(R.id.nav_transitions_object, null, null, extras)
    }


    iv_share_object.setOnClickListener {
      Timber.d("share object")
      val intent = Intent(activity, RenderScriptActivity::class.java)
      val options =
        ActivityOptions.makeSceneTransitionAnimation(activity, iv_share_object, "share_object")
      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    val data = listOf(R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four)
    val listAdapter = TransitionsAdapter { v, resId, pos ->
      val bundle = bundleOf("resId" to resId)
      Timber.d("click pos $pos")
      val extras = FragmentNavigatorExtras(
        v to v.transitionName,
        iv_share_object to iv_share_object.transitionName
      )
      findNavController().navigate(R.id.nav_transitions_object, bundle, null, extras)
    }
    listAdapter.submitList(data)
    // When user hits back button transition takes backward
    postponeEnterTransition()
    recycler_view.adapter = listAdapter
    recycler_view.layoutManager =
      LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    recycler_view.doOnPreDraw {
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
