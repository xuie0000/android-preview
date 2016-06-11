package com.xuie.androiddemo.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.bean.dribbble.Shot;
import com.xuie.androiddemo.model.ShotModelImpl;
import com.xuie.androiddemo.presenter.ShotsPresenter;
import com.xuie.androiddemo.view.fragment.SingleShotFragment;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    List<SingleShotFragment> fragments = new ArrayList<>();
    int mCurrentPosition = -1;
    ViewPager mViewPager;

    List<Shot> mShots = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, ViewPager viewPager) {
        super(fm);
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(10);
    }

    public void update(List<Shot> list, int position) {
        this.mCurrentPosition = position;
        mShots.clear();
        mShots.addAll(list);
        for (Shot shot : mShots) {
//            fragments.add(SingleShotFragment.newInstance(shot));
            fragments.add(new SingleShotFragment(shot));
        }
        notifyDataSetChanged();
    }

    public void addMoreData(List<Shot> datas, int current_page) {

        mShots.addAll(datas);
        this.mCurrentPosition = current_page;
        for (Shot shot : mShots) {
//            fragments.add(SingleShotFragment.newInstance(shot));
            fragments.add(new SingleShotFragment(shot));
        }
        notifyDataSetChanged();
    }

    public void addDatasAndPosition(int position, List<Shot> datas) {
        this.mCurrentPosition = position;
        int index = fragments.size() - 1;
        for (int i = datas.size(); i > 0; i--) {
            fragments.get(index).setData(datas.get(i - 1));
            index--;
        }
    }

    public void addDatasAndPosition(int position, int count) {
        this.mCurrentPosition = position;
        for (int i = 0; i < count; i++) {
            fragments.add(new SingleShotFragment());
        }
        notifyDataSetChanged();
    }

    @Override public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override public int getCount() {
        return fragments.size();
    }

    @Override public void onPageSelected(int position) {
        mCurrentPosition = position;
        if (position == fragments.size() - 1) {
            //这里更新数据了
            addDatasAndPosition(position, 10);
            ShotModelImpl.getInstance()
                    .getShotsFromServer(ShotsPresenter.page, 10)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Shot>>() {
                        @Override
                        public void call(List<Shot> shots) {
                            addDatasAndPosition(position, shots);
//xujie                            ShotAdapter.dataList.addAll(shots);
                            mShots.addAll(shots);
                            ++ShotsPresenter.page;
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                        }
                    });
        }
    }

    @Override public void onPageScrollStateChanged(int state) {

    }

    @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}