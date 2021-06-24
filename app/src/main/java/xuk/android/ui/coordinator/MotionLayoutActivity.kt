package xuk.android.ui.coordinator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import xuk.android.databinding.ActivityMotionLayoutBinding

class MotionLayoutActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMotionLayoutBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ActivityMotionLayoutBinding.inflate(layoutInflater).apply {
      binding = this
      setContentView(root)

      val debugMode = if (intent.getBooleanExtra("showPaths", true)) {
        MotionLayout.DEBUG_SHOW_PATH
      } else {
        MotionLayout.DEBUG_SHOW_NONE
      }
      motionLayout.setDebugMode(debugMode)
    }
  }

  fun changeState(v: View?) {
    if (binding.motionLayout.progress > 0.5f) {
      binding.motionLayout.transitionToStart()
    } else {
      binding.motionLayout.transitionToEnd()
    }
  }

}