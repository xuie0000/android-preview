package com.xuie.androiddemo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.fragment.CustomViewFragment;
import com.xuie.androiddemo.fragment.RecyclerViewFragment;
import com.xuie.androiddemo.fragment.RxJavaFragment;
import com.xuie.androiddemo.fragment.TransitionsFragment;
import com.xuie.generalutils.ScreenUtils;
import com.xuie.generalutils.UpdateManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Bind(R.id.fragment_placeholder)
    FrameLayout fragmentPlaceholder;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private UpdateManager mUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, new CustomViewFragment()).commit();
        toolbar.setTitle("自定义View");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void screenInfoToString() {

        Log.d("xuie", "Width:" + ScreenUtils.getWidth(this));
        Log.d("xuie", "Height:" + ScreenUtils.getHeight(this));
        Log.d("xuie", "StatusBarHeight:" + ScreenUtils.getStatusBarHeight(this));
        Log.d("xuie", "NavigationBarHeight:" + ScreenUtils.getNavigationBarHeight(this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_update) {
            //这里来检测版本是否需要更新
            mUpdateManager = new UpdateManager(this);
            mUpdateManager.checkUpdateInfo();
        }

       return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transitions) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, new TransitionsFragment()).commit();
            toolbar.setTitle("过渡动画");
        } else if (id == R.id.nav_custom_view) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, new CustomViewFragment()).commit();
            toolbar.setTitle("自定义View");
        } else if (id == R.id.nav_rx_java) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, new RxJavaFragment()).commit();
            toolbar.setTitle("RxJava测试");
        } else if (id == R.id.nav_recycler_view) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, new RecyclerViewFragment()).commit();
            toolbar.setTitle("RecyclerView示例");
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
