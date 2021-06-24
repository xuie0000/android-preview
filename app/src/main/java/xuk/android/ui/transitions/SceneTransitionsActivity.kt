package xuk.android.ui.transitions

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber
import xuk.android.databinding.ActivitySceneTransitionsBinding
import xuk.android.util.dp2px

class SceneTransitionsActivity : AppCompatActivity() {

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
  private lateinit var binding: ActivitySceneTransitionsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    when (intent.getIntExtra("flag", 0)) {
      0 -> window.enterTransition = Explode()
      1 -> {
        val slide = Slide()
        slide.interpolator = OvershootInterpolator()
        window.enterTransition = slide
      }
      2 -> {
        window.enterTransition = Fade()
        window.exitTransition = Fade()
      }
      else -> window.enterTransition = Explode()
    }

    binding = ActivitySceneTransitionsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.toolbar.title = "场景动画"
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    initBottomSheetBehavior()

    binding.fabButton.setOnClickListener {
      bottomSheetBehavior.isHideable = true
      when (bottomSheetBehavior.state) {
        BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
        BottomSheetBehavior.STATE_HIDDEN -> bottomSheetBehavior.state =
          BottomSheetBehavior.STATE_COLLAPSED
        BottomSheetBehavior.STATE_DRAGGING -> {
        }
        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
        }
        BottomSheetBehavior.STATE_SETTLING -> {
        }
      }
      bottomSheetBehavior.setHideable(false)
    }
  }

  private fun initBottomSheetBehavior() {
    // init the bottom sheet behavior
    bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

    // change the state of the bottom sheet
    /**
     * @link BottomSheetBehavior.STATE_COLLAPSED 收紧
     * @link BottomSheetBehavior.STATE_EXPANDED 展开
     * @link BottomSheetBehavior.STATE_HIDDEN 隐藏
     */
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    // set the peek height
    bottomSheetBehavior.peekHeight = dp2px(80f)

    // set hide or not
    bottomSheetBehavior.isHideable = false

    // set callback for changes
    bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onStateChanged(bottomSheet: View, newState: Int) {
        Timber.d("onStateChanged: $newState")
      }

      override fun onSlide(bottomSheet: View, slideOffset: Float) {

      }
    })
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
