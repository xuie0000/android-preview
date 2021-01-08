package xuk.android.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_transitions.*
import xuk.android.R
import xuk.android.ui.renderscript.RenderScriptActivity
import xuk.android.util.log

class TransitionsFragment : Fragment(R.layout.fragment_transitions) {

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    make_scene_transition_animation.setOnClickListener {
      val intent = Intent(activity, SecondActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, make_scene_transition_animation, "share01")
      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    fab_button.setOnClickListener {
      log { "fab_button" }
      val intent = Intent(activity, SecondActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity,
          // 创建多个共享元素
          Pair.create(make_scene_transition_animation, "share01"),
          Pair.create(fab_button, "share02"))

      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    explode.setOnClickListener { makeSceneTransitionAnimationNoParameter(0) }
    slide.setOnClickListener { makeSceneTransitionAnimationNoParameter(1) }
    fade.setOnClickListener { makeSceneTransitionAnimationNoParameter(2) }

    iv_share_object.setOnClickListener {
      log { "share object" }
      val intent = Intent(activity, RenderScriptActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, iv_share_object, "share_object")
      ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }
  }

  private fun makeSceneTransitionAnimationNoParameter(flag: Int) {
    val intent = Intent(activity, SecondActivity::class.java)
    intent.putExtra("flag", flag)
    val options = ActivityOptions.makeSceneTransitionAnimation(activity)
    ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
  }
}
