package com.xuie.androiddemo.ui.activity.shot;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.dribbble.Shot;
import com.xuie.androiddemo.bean.dribbble.User;
import com.xuie.androiddemo.ui.activity.shot.presenter.ShotPresenter;
import com.xuie.androiddemo.ui.activity.BaseActivity;
import com.xuie.androiddemo.util.PreferenceUtils;
import com.xuie.androiddemo.ui.adapter.ViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotActivity extends BaseActivity<ShotActivity, ShotPresenter> implements IShotActivity<Shot>{

    ViewPagerAdapter mAdapter;
    int currentPosition;
    List shotList;

    @BindView(R.id.view_pager) ViewPager viewPager;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_shot);
        ButterKnife.bind(this);

        currentPosition = getIntent().getIntExtra("position", 1);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), viewPager);
        mPresenter.loadDataFromRealm();
    }

    @Override protected void onPause() {
        super.onPause();
        PreferenceUtils.setPref(this, "current_position", currentPosition);
    }

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override protected ShotPresenter createPresenter() {
        return new ShotPresenter();
    }

    @Override public void showShots(List<Shot> list, int current_page) {
        if (shotList == null) {
            shotList = list;
            mAdapter.update(shotList, currentPosition);
            viewPager.setAdapter(mAdapter);
            viewPager.setCurrentItem(currentPosition);
        } else {
            mAdapter.addMoreData(list, current_page);
        }
    }

    @Override public void uploadUserInfo(User user) {

    }
}
