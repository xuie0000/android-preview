package xuk.android.ui.coordinator

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import xuk.android.databinding.ActivityCoordinatorLayoutBinding

/**
 * @author Jie Xu
 */
class CoordinatorLayoutActivity : AppCompatActivity() {

  private lateinit var binding: ActivityCoordinatorLayoutBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    ActivityCoordinatorLayoutBinding.inflate(layoutInflater).apply {
      binding = this
      setContentView(root)

      setSupportActionBar(toolbar)
      supportActionBar?.setDisplayHomeAsUpEnabled(true)

      fab.setOnClickListener { view ->
        Snackbar.make(
          view,
          "Replace with your own action",
          Snackbar.LENGTH_LONG
        ).setAction("Action", null).show()
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
