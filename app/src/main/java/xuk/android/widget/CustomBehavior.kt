package xuk.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.core.widget.NestedScrollView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton

val INTERPOLATOR = FastOutSlowInInterpolator()

class CustomBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {
  private var mIsAnimatingOut = false

  private var previousY = 0f

  override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
    return dependency is NestedScrollView
  }

  override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
    val y = dependency.y
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

  }

  // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
  private fun animateIn(button: FloatingActionButton) {
    button.visibility = View.VISIBLE
    ViewCompat.animate(button)
        .scaleX(1.0f)
        .scaleY(1.0f)
        .alpha(1.0f)
        .setInterpolator(INTERPOLATOR)
        .withLayer()
        .setListener(null)
        .start()

  }
}
