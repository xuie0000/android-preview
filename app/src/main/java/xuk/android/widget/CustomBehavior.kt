package xuk.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.core.widget.NestedScrollView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xuk.android.R

/**
 *
 * @author Jie Xu
 * @date 16-9-6
 * From Drakeet <drakeet.me></drakeet.me>@gmail.com> Meizhi
 */
val INTERPOLATOR = FastOutSlowInInterpolator()

@SuppressLint("RestrictedApi")
class CustomBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {
  private var mIsAnimatingOut = false
  private val mIsScrollUp = false

  private var previousY = 0f

  override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
    return dependency is NestedScrollView
  }

  override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
    val y = dependency.y
    //        Log.d(TAG, "onDependentViewChanged: " + y + ", " + previousY + ", " + (child.getVisibility() == View.VISIBLE));
    if (y - previousY > 0 && child.visibility != View.VISIBLE) {
      animateIn(child)
    } else if (y - previousY < 0 && child.visibility == View.VISIBLE) {
      animateOut(child)
    }

    previousY = dependency.y
    return true
  }

  // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
  private fun animateOut(button: FloatingActionButton) {
    if (Build.VERSION.SDK_INT >= 14) {
      ViewCompat.animate(button)
          .scaleX(0.0f)
          .scaleY(0.0f)
          .alpha(0.0f)
          .setInterpolator(INTERPOLATOR)
          .withLayer()
          .setListener(object : ViewPropertyAnimatorListener {
            override fun onAnimationStart(view: View) {
              this@CustomBehavior.mIsAnimatingOut = true
            }

            override fun onAnimationCancel(view: View) {
              this@CustomBehavior.mIsAnimatingOut = false
            }

            override fun onAnimationEnd(view: View) {
              this@CustomBehavior.mIsAnimatingOut = false
              view.visibility = View.GONE
            }
          })
          .start()
    } else {
      val anim = AnimationUtils.loadAnimation(button.context, R.anim.design_fab_out)
      anim.interpolator = INTERPOLATOR
      anim.duration = 200L
      anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
          this@CustomBehavior.mIsAnimatingOut = true
        }

        override fun onAnimationEnd(animation: Animation) {
          this@CustomBehavior.mIsAnimatingOut = false
          button.visibility = View.GONE
        }

        override fun onAnimationRepeat(animation: Animation) {}
      })
      button.startAnimation(anim)
    }
  }

  // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
  private fun animateIn(button: FloatingActionButton) {
    button.visibility = View.VISIBLE
    if (Build.VERSION.SDK_INT >= 14) {
      ViewCompat.animate(button)
          .scaleX(1.0f)
          .scaleY(1.0f)
          .alpha(1.0f)
          .setInterpolator(INTERPOLATOR)
          .withLayer()
          .setListener(null)
          .start()
    } else {
      val anim = AnimationUtils.loadAnimation(button.context, R.anim.design_fab_in)
      anim.duration = 200L
      anim.interpolator = INTERPOLATOR
      button.startAnimation(anim)
    }
  }
}
