package xuk.android.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_transitions.*
import xuk.android.R
import xuk.android.util.log
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class TransitionsFragment : Fragment() {

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_transitions, container, false)
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    make_scene_transition_animation.setOnClickListener { v ->
      val intent = Intent(activity, OneActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, make_scene_transition_animation, "share01")
      ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
    }

    fab_button.setOnClickListener { v ->
      log { "fab_button" }
      val intent = Intent(activity, OneActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity,
          // 创建多个共享元素
          Pair.create<View, String>(make_scene_transition_animation, "share01"),
          Pair.create<View, String>(fab_button, "share02")
      )

      ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
    }

    explode!!.setOnClickListener { v -> makeSceneTransitionAnimationNoParameter(0) }
    slide!!.setOnClickListener { v -> makeSceneTransitionAnimationNoParameter(1) }
    fade!!.setOnClickListener { v -> makeSceneTransitionAnimationNoParameter(2) }

    over_shoot.setOnClickListener { v ->
      log { "over_shoot" }
      val intent = Intent(activity, TransitionsObjectActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, over_shoot, "shareOverShoot")
      ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
    }

    cir_reveal_btn.setOnClickListener { v ->
      // http://blog.jobbole.com/77015/ 圆形显示
      if (cir_reveal_dst.visibility != View.VISIBLE) {
        val anim = ViewAnimationUtils.createCircularReveal(
            cir_reveal_dst,
            cir_reveal_dst.width / 2,
            cir_reveal_dst.height / 2,
            0f,
            Math.max(cir_reveal_dst.width, cir_reveal_dst.height).toFloat())

        anim.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            cir_reveal_dst.visibility = View.VISIBLE
          }
        })

        anim.start()
      } else {
        val anim = ViewAnimationUtils.createCircularReveal(
            cir_reveal_dst,
            cir_reveal_dst.width / 2,
            cir_reveal_dst.height / 2,
            Math.max(cir_reveal_dst.width, cir_reveal_dst.height).toFloat(),
            0f)

        anim.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            cir_reveal_dst.visibility = View.INVISIBLE
          }
        })

        anim.start()
      }
    }

    cir_reveal_normal.setOnClickListener { v ->
      val mNormalAnimator = ViewAnimationUtils.createCircularReveal(
          cir_reveal_normal,
          cir_reveal_normal.width / 2,
          cir_reveal_normal.height / 2,
          0f,
          Math.max(cir_reveal_normal.width, cir_reveal_normal.height).toFloat())
      mNormalAnimator.interpolator = AccelerateInterpolator()
      mNormalAnimator.start()
    }

    cir_reveal_hypot.setOnClickListener { v ->
      //勾股定理得到斜边长度
      val endRadius = Math.hypot(cir_reveal_hypot.width.toDouble(),
          cir_reveal_hypot.height.toDouble()).toFloat()
      val mHypotAnimator = ViewAnimationUtils.createCircularReveal(cir_reveal_hypot,
          cir_reveal_hypot.width, 0, 0f, endRadius)
      mHypotAnimator.interpolator = AccelerateDecelerateInterpolator()
      mHypotAnimator.start()
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private fun makeSceneTransitionAnimationNoParameter(flag: Int) {
    val intent = Intent(activity, OneActivity::class.java)
    intent.putExtra("flag", flag)
    val options = ActivityOptions.makeSceneTransitionAnimation(activity)
    ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
  }
}
