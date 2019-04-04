package com.xuie.android.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuie.android.BuildConfig;
import com.xuie.android.R;
import com.xuie.android.ui.coordinator.CoordinatorLayoutActivity;
import com.xuie.android.ui.palette.PaletteActivity;
import com.xuie.android.util.PreferenceUtils;
import com.xuie.android.util.Utils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author xuie
 */
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private int mDayNightMode;
    private ShareActionProvider shareActionProvider;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navView = findViewById(R.id.nav_view);
        FloatingActionButton fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        appBarConfiguration = new AppBarConfiguration.Builder()
                .setDrawerLayout(drawerLayout)
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fab.setOnClickListener(v -> {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
//            if (BuildConfig.DEBUG) {
//                CrashReport.testJavaCrash()
//            }
        });
        // Calls onNavDestinationSelected(MenuItem, NavController) when menu item selected
        NavigationUI.setupWithNavController(navView, navController);
        // Changes title, animates hamburger to back/up icon
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
//        navController.navigate(R.id.action_to_test)

        mDayNightMode = PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        appTask();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Ref: https://developer.android.com/reference/androidx/navigation/ui/NavigationUI
        // This _should_ be correct for case w/nav drawer
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        setShareIntent();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsDenied finish.");
        finish();
    }

    private void setShareIntent() {
        shareActionProvider.setShareIntent(Utils.getDefaultIntent(this));
    }

    private static final int RC_STORAGE_PERM = 123;

    @AfterPermissionGranted(RC_STORAGE_PERM)
    private void appTask() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            setShareIntent();
        } else {
            EasyPermissions.requestPermissions(this, "需要重新申请分享权限", RC_STORAGE_PERM, perms);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_day_night_yes) {
            refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (item.getItemId() == R.id.action_day_night_no) {
            refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (item.getItemId() == R.id.action_palette) {
            startPalette();
        } else if (item.getItemId() == R.id.action_coordinatorLayout) {
            startCoordinatorLayout();
        }
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        int uiMode = getResources().getConfiguration().uiMode;
//        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // enable the save mode
        int defaultMode = PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
        if (mDayNightMode != defaultMode) {
            refreshDelegateMode(defaultMode);
        }
    }

    public void refreshDelegateMode(int mode) {
        mDayNightMode = mode;
        PreferenceUtils.setPreference("mode", mode);
        getDelegate().setLocalNightMode(mode);
        recreate();
    }

    public void startPalette() {
        startActivity(new Intent(this, PaletteActivity.class));
    }

    public void startCoordinatorLayout() {
        startActivity(new Intent(this, CoordinatorLayoutActivity.class));
    }
}
