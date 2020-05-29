package xuk.android.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import xuk.android.R
import xuk.android.ui.coordinator.CoordinatorLayoutActivity
import xuk.android.ui.palette.PaletteActivity
import xuk.android.util.log
import xuk.android.util.screenShotToUri

/**
 * @author Jie Xu
 */
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var shareActionProvider: ShareActionProvider

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    log { "onCreate" }
    setSupportActionBar(toolbar)

    fab.setOnClickListener { v ->
      Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()
    }

    val navController = findNavController(R.id.nav_host_fragment)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    appBarConfiguration = AppBarConfiguration(setOf(
        R.id.nav_transitions, R.id.nav_test, R.id.nav_recycler), drawer_layout)
    setupActionBarWithNavController(navController, appBarConfiguration)
    nav_view.setupWithNavController(navController)

    appTask()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    val item = menu.findItem(R.id.action_share)
    shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
    shareActionProvider.setShareIntent(Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_STREAM, this@MainActivity.screenShotToUri())
      type = "image/*"
    })
    return true
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment)
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    // Forward results to EasyPermissions
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
  }

  override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
    // init do something
  }

  override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
    log { "onPermissionsDenied finish." }

    finish()
  }

  @AfterPermissionGranted(RC_STORAGE_PERM)
  private fun appTask() {
    val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    if (EasyPermissions.hasPermissions(this, *perms)) {
      log { "request permissions failed!" }
    } else {
      EasyPermissions.requestPermissions(this, "需要重新申请分享权限", RC_STORAGE_PERM, *perms)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when {
      item.itemId == R.id.action_palette -> {
        startActivity(PaletteActivity::class.java)
        true
      }
      item.itemId == R.id.action_coordinator_layout -> {
        startActivity(CoordinatorLayoutActivity::class.java)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun startActivity(cls: Class<*>) {
    startActivity(Intent(this, cls))
  }

  companion object {
    private const val RC_STORAGE_PERM = 123
  }
}
