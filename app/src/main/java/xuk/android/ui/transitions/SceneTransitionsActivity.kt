package xuk.android.ui.transitions

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.MenuItem
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import xuk.android.databinding.ActivitySceneTransitionsBinding

class SceneTransitionsActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySceneTransitionsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    when (intent.getIntExtra("flag", 0)) {
      0 -> window.enterTransition = Explode()
      1 -> {
        window.enterTransition = Slide().apply {
          interpolator = OvershootInterpolator()
        }
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

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
