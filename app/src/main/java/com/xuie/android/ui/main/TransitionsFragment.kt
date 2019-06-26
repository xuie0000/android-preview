package com.xuie.android.ui.main

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
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.xuie.android.R
import com.xuie.android.util.log
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author xuie
 */
class TransitionsFragment : Fragment() {
  private var makeSceneTransitionAnimation: Button? = null
  private var fabButton: Button? = null
  private var overShoot: ImageView? = null
  private var cirRevealDst: ImageView? = null
  private var cirRevealHypot: ImageView? = null
  private var cirRevealNormal: ImageView? = null
  private var explode: Button? = null
  private var slide: Button? = null
  private var fade: Button? = null
  private var cirRevealBtn: Button? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_transitions, container, false)
    makeSceneTransitionAnimation = view.findViewById(R.id.make_scene_transition_animation)
    fabButton = view.findViewById(R.id.fab_button)
    overShoot = view.findViewById(R.id.over_shoot)
    cirRevealDst = view.findViewById(R.id.cir_reveal_dst)
    cirRevealHypot = view.findViewById(R.id.cir_reveal_hypot)
    cirRevealNormal = view.findViewById(R.id.cir_reveal_normal)
    explode = view.findViewById(R.id.explode)
    slide = view.findViewById(R.id.slide)
    fade = view.findViewById(R.id.fade)
    cirRevealBtn = view.findViewById(R.id.cir_reveal_btn)
    return view
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    makeSceneTransitionAnimation!!.setOnClickListener { v ->
      val intent = Intent(activity, OneActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, makeSceneTransitionAnimation, "share01")
      ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
    }

    fabButton!!.setOnClickListener { v ->
      log { "fab_button" }
      val intent = Intent(activity, OneActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity,
          // 创建多个共享元素
          Pair.create<View, String>(makeSceneTransitionAnimation, "share01"),
          Pair.create<View, String>(fabButton, "share02")
      )

      ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
    }

    explode!!.setOnClickListener { v -> makeSceneTransitionAnimationNoParameter(0) }
    slide!!.setOnClickListener { v -> makeSceneTransitionAnimationNoParameter(1) }
    fade!!.setOnClickListener { v -> makeSceneTransitionAnimationNoParameter(2) }

    overShoot!!.setOnClickListener { v ->
      log { "over_shoot" }
      val intent = Intent(activity, TransitionsObjectActivity::class.java)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, overShoot, "shareOverShoot")
      ActivityCompat.startActivity(Objects.requireNonNull<FragmentActivity>(activity), intent, options.toBundle())
    }


    cirRevealBtn!!.setOnClickListener { v ->
      // http://blog.jobbole.com/77015/ 圆形显示
      if (cirRevealDst!!.visibility != View.VISIBLE) {
        val anim = ViewAnimationUtils.createCircularReveal(
            cirRevealDst,
            cirRevealDst!!.width / 2,
            cirRevealDst!!.height / 2,
            0f,
            Math.max(cirRevealDst!!.width, cirRevealDst!!.height).toFloat())

        anim.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            cirRevealDst!!.visibility = View.VISIBLE
          }
        })

        anim.start()
      } else {
        val anim = ViewAnimationUtils.createCircularReveal(
            cirRevealDst,
            cirRevealDst!!.width / 2,
            cirRevealDst!!.height / 2,
            Math.max(cirRevealDst!!.width, cirRevealDst!!.height).toFloat(),
            0f)

        anim.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            cirRevealDst!!.visibility = View.INVISIBLE
          }
        })

        anim.start()
      }
    }

    cirRevealNormal!!.setOnClickListener { v ->
      val mNormalAnimator = ViewAnimationUtils.createCircularReveal(
          cirRevealNormal,
          cirRevealNormal!!.width / 2,
          cirRevealNormal!!.height / 2,
          0f,
          Math.max(cirRevealNormal!!.width, cirRevealNormal!!.height).toFloat())
      mNormalAnimator.interpolator = AccelerateInterpolator()
      mNormalAnimator.start()
    }

    cirRevealHypot!!.setOnClickListener { v ->
      //勾股定理得到斜边长度
      val endRadius = Math.hypot(cirRevealHypot!!.width.toDouble(),
          cirRevealHypot!!.height.toDouble()).toFloat()
      val mHypotAnimator = ViewAnimationUtils.createCircularReveal(cirRevealHypot,
          cirRevealHypot!!.width, 0, 0f, endRadius)
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
