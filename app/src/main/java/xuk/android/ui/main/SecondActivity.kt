package xuk.android.ui.main

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
import kotlinx.android.synthetic.main.activity_second.*
import xuk.android.R
import xuk.android.util.dp2px
import xuk.android.util.log

class SecondActivity : AppCompatActivity() {

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

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

    setContentView(R.layout.activity_second)
    toolbar.title = SecondActivity::class.java.simpleName
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    initBottomSheetBehavior()

    fab_button.setOnClickListener {
      bottomSheetBehavior.isHideable = true
      when (bottomSheetBehavior.state) {
        BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
        BottomSheetBehavior.STATE_HIDDEN -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
      }
      bottomSheetBehavior.setHideable(false)
    }
  }

  private fun initBottomSheetBehavior() {
    // init the bottom sheet behavior
    bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

    // change the state of the bottom sheet
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

    // set the peek height
    bottomSheetBehavior.peekHeight = dp2px(80f)

    // set hide or not
    bottomSheetBehavior.isHideable = false

    // set callback for changes
    bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onStateChanged(bottomSheet: View, newState: Int) {
        log { "onStateChanged: $newState" }
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
