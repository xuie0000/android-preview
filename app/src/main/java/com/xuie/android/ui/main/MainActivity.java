package com.xuie.android.ui.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.xuie.android.R;
import com.xuie.android.ui.activity.TwoActivity;
import com.xuie.android.ui.coordinatorLayout.CoordinatorLayoutActivity;
import com.xuie.android.ui.diffutil.DiffUtilFragment;
import com.xuie.android.ui.palette.PaletteActivity;
import com.xuie.android.ui.weather.WeatherDataBindingActivity;
import com.xuie.android.util.BitmapUtils;
import com.xuie.android.util.PreferenceUtils;
import com.xuie.android.util.ShortcutUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final String KEY_FRAGMENT = "currentFragment";

    private int mDayNightMode;
    private ShareActionProvider shareActionProvider;

    private int currentFragmentId = -1;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

        mDayNightMode = PreferenceUtils.getInt(this, "mode", AppCompatDelegate.MODE_NIGHT_NO);

        if (PreferenceUtils.getBoolean(this, "first_run", true)) {
            PreferenceUtils.setPreference(this, "first_run", false);
            Logger.d("create a shortcut");
            // ShortcutUtils.addShortcutByPackageName(this, getPackageName());
            ShortcutUtils.addShortcut(this, getShortCutIntent(), getString(R.string.app_name), false, BitmapFactory.decodeResource(getResources(), R.mipmap.image_small));
        }
    }

    private void switchNavigation(int navId) {
        switch (navId) {
            case R.id.nav_test:
                switchFragment(TestFragment.class.getName(), getString(R.string.test));
                break;
            case R.id.nav_animation:
                switchFragment(AnimationFragment.class.getName(), getString(R.string.animation));
                break;
            case R.id.nav_transitions:
                switchFragment(TransitionsFragment.class.getName(), getString(R.string.transitions));
                break;
            case R.id.nav_recycler_view:
                switchFragment(RecyclerViewFragment.class.getName(), getString(R.string.recycler_view));
                break;
            case R.id.nav_diff_util:
                switchFragment(DiffUtilFragment.class.getName(), getString(R.string.diff_util));
                break;
        }

        currentFragmentId = navId;
    }

    private Intent getShortCutIntent() {
        // 使用MAIN，可以避免部分手机(比如华为、HTC部分机型)删除应用时无法删除快捷方式的问题
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setClass(MainActivity.this, TwoActivity.class);
        return intent;
    }

    @Override public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_FRAGMENT, currentFragmentId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        shareActionProvider.setShareIntent(getDefaultIntent());
                    } else {
                        finish();
                    }
                });
        return true;
    }

    private Intent getDefaultIntent() {
        File file = BitmapUtils.Drawable2File(this, R.mipmap.ic_launcher, Environment.getExternalStorageDirectory() + "/test.png");
        Uri uri = BitmapUtils.File2Uri(file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        return shareIntent;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_day_night_yes) {
            refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (item.getItemId() == R.id.action_day_night_no) {
            refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (item.getItemId() == R.id.action_weather) {
            startWeather();
        } else if (item.getItemId() == R.id.action_palette) {
            startPalette();
        } else if (item.getItemId() == R.id.action_coordinatorLayout) {
            startCoordinatorLayout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        int uiMode = getResources().getConfiguration().uiMode;
//        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // enable the save mode
        int defaultMode = PreferenceUtils.getInt(this, "mode", AppCompatDelegate.MODE_NIGHT_NO);
//        Logger.d(defaultMode + ", " + mDayNightMode + ", " + dayNightUiMode + " .");
        if (mDayNightMode != defaultMode) {
            refreshDelegateMode(defaultMode);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void switchFragment(String fragName, String fragTitle) {
        try {
            Fragment fragment = (Fragment) Class.forName(fragName).newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Logger.e(e.getMessage());
        }
        toolbar.setTitle(fragTitle);
    }

    public void refreshDelegateMode(int mode) {
        mDayNightMode = mode;
        PreferenceUtils.setPreference(this, "mode", mode);
        getDelegate().setLocalNightMode(mode);
        recreate();
    }

    public void startWeather() {
        startActivity(new Intent(this, WeatherDataBindingActivity.class));
    }

    public void startPalette() {
        startActivity(new Intent(this, PaletteActivity.class));
    }

    public void startCoordinatorLayout() {
        startActivity(new Intent(this, CoordinatorLayoutActivity.class));
    }
}
