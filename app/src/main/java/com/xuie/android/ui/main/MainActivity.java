package com.xuie.android.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.xuie.android.R;
import com.xuie.android.ui.recyclerView.RecyclerViewFragment;
import com.xuie.android.ui.recyclerView.axis.AxisFragment;
import com.xuie.android.ui.coordinatorLayout.CoordinatorLayoutActivity;
import com.xuie.android.ui.recyclerView.diffutil.DiffUtilFragment;
import com.xuie.android.ui.palette.PaletteActivity;
import com.xuie.android.ui.recyclerView.normal.NormalFragment;
import com.xuie.android.util.PreferenceUtils;
import com.xuie.android.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private final String KEY_FRAGMENT = "currentFragment";

    private int mDayNightMode;
    private ShareActionProvider shareActionProvider;

    private int currentFragmentId = -1;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.fab) FloatingActionButton fab;

    private FragmentManager fragmentManager;

    private TestFragment testFragment;
    private TransitionsFragment transitionsFragment;
    private RecyclerViewFragment recyclerViewFragment;
    private DiffUtilFragment diffUtilFragment;
    private AxisFragment axisFragment;

    protected Fragment currentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            currentFragmentId = (int) savedInstanceState.getSerializable(KEY_FRAGMENT);
            switchNavigation(currentFragmentId);
        } else {
            switchNavigation(R.id.nav_transitions);
        }
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fab.setOnClickListener(v -> Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());

        navView.setNavigationItemSelectedListener(item -> {
            switchNavigation(item.getItemId());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        mDayNightMode = PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void switchNavigation(int navId) {
        switch (navId) {
            case R.id.nav_test:
                if (testFragment == null) {
                    testFragment = new TestFragment();
                }
                addOrShowFragment(testFragment);
                break;
            case R.id.nav_transitions:
                if (transitionsFragment == null) {
                    transitionsFragment = new TransitionsFragment();
                }
                addOrShowFragment(transitionsFragment);
                break;
            case R.id.nav_recycler_view:
                if (recyclerViewFragment == null) {
                    recyclerViewFragment = new RecyclerViewFragment();
                }
                addOrShowFragment(recyclerViewFragment);
                break;
        }

        currentFragmentId = navId;
    }

    private void addOrShowFragment(Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (fragment == null)
            return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (currentFragment == null) {
            transaction
                    .add(R.id.fragment_placeholder, fragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (fragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .show(fragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fragment_placeholder, fragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
        currentFragment = fragment;

        setToolbar();
    }

    private void setToolbar() {
        if (currentFragment instanceof TestFragment) {
            toolbar.setTitle(getString(R.string.test));
        } else if (currentFragment instanceof TransitionsFragment) {
            toolbar.setTitle(getString(R.string.transitions));
        } else if (currentFragment instanceof NormalFragment) {
            toolbar.setTitle(getString(R.string.recycler_view));
        } else if (currentFragment instanceof DiffUtilFragment) {
            toolbar.setTitle(getString(R.string.diff_util));
        } else if (currentFragment instanceof AxisFragment) {
            toolbar.setTitle(getString(R.string.axis));
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null && currentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            currentFragment.getChildFragmentManager().popBackStack();
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_FRAGMENT, currentFragmentId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        setShareIntent();
    }

    private void setShareIntent() {
        shareActionProvider.setShareIntent(Utils.getDefaultIntent(this));
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Logger.d("onPermissionsDenied finish.");
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        appTask();
        return true;
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        int uiMode = getResources().getConfiguration().uiMode;
//        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // enable the save mode
        int defaultMode = PreferenceUtils.getInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
//        Logger.d(defaultMode + ", " + mDayNightMode + ", " + dayNightUiMode + " .");
        if (mDayNightMode != defaultMode) {
            refreshDelegateMode(defaultMode);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
