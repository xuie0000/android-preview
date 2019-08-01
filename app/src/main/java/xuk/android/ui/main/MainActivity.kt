package xuk.android.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import xuk.android.R
import xuk.android.ui.coordinator.CoordinatorLayoutActivity
import xuk.android.ui.pager2.PageActivity
import xuk.android.ui.palette.PaletteActivity
import xuk.android.util.Utils
import xuk.android.util.log

/**
 * @author Jie Xu
 */
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
  private lateinit var shareActionProvider: ShareActionProvider

  private lateinit var drawerLayout: DrawerLayout
  private lateinit var navController: NavController

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    log { "onCreate" }

    navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    drawerLayout = findViewById(R.id.drawer_layout)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    val navigationView = findViewById<NavigationView>(R.id.nav_view)
    val fab = findViewById<FloatingActionButton>(R.id.fab)

    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    fab.setOnClickListener { v ->
      Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()
//      if (BuildConfig.DEBUG) {
//        CrashReport.testJavaCrash()
//      }
    }

    // Calls onNavDestinationSelected(MenuItem, NavController) when menu item selected
    NavigationUI.setupWithNavController(navigationView, navController)
    // Changes title, animates hamburger to back/up icon
    NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

    appTask()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    log { "onCreateOptionsMenu" }
    menuInflater.inflate(R.menu.main, menu)
    val item = menu.findItem(R.id.action_share)
    shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
    shareActionProvider.setShareIntent(Utils.getDefaultIntent(this))
    return true
  }

  override fun onSupportNavigateUp(): Boolean {
    // Ref: https://developer.android.com/reference/androidx/navigation/ui/NavigationUI
    // This _should_ be correct for case w/nav drawer
    return NavigationUI.navigateUp(navController, drawerLayout)
  }

  override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
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
    val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (EasyPermissions.hasPermissions(this, *perms)) {
      log { "11111" }
    } else {
      log { "2222" }
      EasyPermissions.requestPermissions(this, "需要重新申请分享权限", RC_STORAGE_PERM, *perms)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when {
      item.itemId == R.id.action_palette -> {
        startPalette()
        true
      }
      item.itemId == R.id.action_coordinator_layout -> {
        startCoordinatorLayout()
        true
      }
      item.itemId == R.id.action_view_pager_2 -> {
        startViewPager2()
        true
      }
      else -> NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }
  }

  private fun startPalette() {
    startActivity(Intent(this, PaletteActivity::class.java))
  }

  private fun startCoordinatorLayout() {
    startActivity(Intent(this, CoordinatorLayoutActivity::class.java))
  }

  private fun startViewPager2() {
    startActivity(Intent(this, PageActivity::class.java))
  }

  companion object {
    private const val RC_STORAGE_PERM = 123
  }
}
