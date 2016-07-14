package com.xuie.androiddemo.ui.activity.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.dribbble.User;
import com.xuie.androiddemo.ui.activity.BaseActivity;
import com.xuie.androiddemo.ui.activity.PaletteActivity;
import com.xuie.androiddemo.ui.activity.login.LoginActivity;
import com.xuie.androiddemo.ui.activity.main.presenter.MainPresenter;
import com.xuie.androiddemo.ui.activity.weather.WeatherActivity;
import com.xuie.androiddemo.ui.fragment.person.PersonFragment;
import com.xuie.androiddemo.util.PreferenceUtils;
import com.xuie.androiddemo.widget.BottomSheetDialogView;
import com.xuie.util.BitmapUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainActivity, MainPresenter> implements IMainActivity {
    private static final int REQUEST_CODE = 1;
    private final String KEY_FRAGMENT = "currentFragment";

    private int mDayNightMode;
    private ShareActionProvider shareActionProvider;

    private int currentFragmentId = -1;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.fab) FloatingActionButton fab;

    private ImageView userAvatar;
    private TextView userName;
    private TextView userDescribe;

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

        userAvatar = ButterKnife.findById(navView.getHeaderView(0), R.id.nav_header_img);
        userName = ButterKnife.findById(navView.getHeaderView(0), R.id.nav_header_name);
        userDescribe = ButterKnife.findById(navView.getHeaderView(0), R.id.nav_header_describe);
        userAvatar.setOnClickListener(v -> {
            if (getPresenter().getCurrentUser() == null)
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_CODE);
                // mDrawerLayout.closeDrawers();
            else {
                switchFragment(PersonFragment.class.getName(), PersonFragment.class.getSimpleName());
            }
        });

        mDayNightMode = PreferenceUtils.getPrefInt(this, "mode", AppCompatDelegate.MODE_NIGHT_NO);

        printVersion();
    }

    private void switchNavigation(int navId) {
        mPresenter.switchNavigation(navId);
        currentFragmentId = navId;
    }

    private void setupUmeng() {
        UmengUpdateAgent.setDeltaUpdate(false);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
//        MobclickAgent.setDebugMode(true);
    }

    private void printVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            System.out.println("Current Version : [" + info.versionName + "," + info.versionCode + "]");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
                        setupUmeng();
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
        mPresenter.optionsItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // enable the save mode
        int defaultMode = PreferenceUtils.getPrefInt(this, "mode", AppCompatDelegate.MODE_NIGHT_NO);
        Logger.d(defaultMode + ", " + mDayNightMode + ", " + dayNightUiMode + " .");
        if (mDayNightMode != defaultMode) {
            refreshDelegateMode(defaultMode);
        }
    }

    @Override protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override public void switchFragment(String fragName, String fragTitle) {
        try {
            Fragment fragment = (Fragment) Class.forName(fragName).newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Logger.e(e.getMessage());
        }
        toolbar.setTitle(fragTitle);
    }

    @Override public void refreshDelegateMode(int mode) {
        mDayNightMode = mode;
        PreferenceUtils.setPref(this, "mode", mode);
        getDelegate().setLocalNightMode(mode);
        recreate();
    }

    @Override public void showBottomSheetDialog() {
        BottomSheetDialogView.show(this, mDayNightMode);
    }

    @Override public void loadUerInfo(User user) {
        mPresenter.loadImageWithUrl(user.getAvatarUrl(), userAvatar);
        userName.setText(user.getUsername());
        userDescribe.setText(user.getHtmlUrl());
    }

    @Override public void startWeather() {
        startActivity(new Intent(this, WeatherActivity.class));
    }

    @Override public void startPalette() {
        startActivity(new Intent(this, PaletteActivity.class));
    }

}
