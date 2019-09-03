package xuk.android.ui.main

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import xuk.android.R
import xuk.android.util.dp2px
import xuk.android.util.log

/**
 * @author Jie Xu
 */
class OneActivity : AppCompatActivity() {

  private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

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

    setContentView(R.layout.activity_one)

    val fabButton = findViewById<Button>(R.id.fab_button)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    val bottomSheet = findViewById<LinearLayout>(R.id.bottom_sheet)

    toolbar.title = OneActivity::class.java.simpleName
    setSupportActionBar(toolbar)
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    // init the bottom sheet behavior
    bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

    // change the state of the bottom sheet
    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN

    // set the peek height
    bottomSheetBehavior!!.peekHeight = dp2px(80f)

    // set hideable or not
    bottomSheetBehavior!!.isHideable = false

    // set callback for changes
    bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onStateChanged(bottomSheet: View, newState: Int) {
        log { "onStateChanged: $newState" }
      }

      override fun onSlide(bottomSheet: View, slideOffset: Float) {

      }
    })

    fabButton.setOnClickListener {
      bottomSheetBehavior!!.isHideable = true
      when (bottomSheetBehavior!!.state) {
        BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
        BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_HIDDEN)
        BottomSheetBehavior.STATE_HIDDEN -> bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
      }
      bottomSheetBehavior!!.setHideable(false)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
