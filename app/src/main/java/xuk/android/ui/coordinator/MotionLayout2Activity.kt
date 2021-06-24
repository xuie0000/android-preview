package xuk.android.ui.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xuk.android.databinding.ActivityMotionLayout2Binding

class MotionLayout2Activity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    ActivityMotionLayout2Binding.inflate(layoutInflater).apply {
      setContentView(root)

    }
  }
}