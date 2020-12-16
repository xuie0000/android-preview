package xuk.android.ui.coordinator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import xuk.android.R

class MotionLayoutActivity : AppCompatActivity() {
  private lateinit var container: MotionLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_motion_layout)

    container = findViewById(R.id.motionLayout)
    val debugMode = if (intent.getBooleanExtra("showPaths", true)) {
      MotionLayout.DEBUG_SHOW_PATH
    } else {
      MotionLayout.DEBUG_SHOW_NONE
    }
    container.setDebugMode(debugMode)
  }

  fun changeState(v: View?) {
    val motionLayout = container as? MotionLayout ?: return
    if (motionLayout.progress > 0.5f) {
      motionLayout.transitionToStart()
    } else {
      motionLayout.transitionToEnd()
    }
  }

}