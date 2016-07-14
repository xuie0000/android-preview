package com.xuie.androiddemo.ui.activity.main.presenter;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.dribbble.User;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.ImageModelImpl;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.presenter.BasePresenter;
import com.xuie.androiddemo.util.SPUtil;
import com.xuie.androiddemo.util.ToastUtil;
import com.xuie.androiddemo.util.UserUtil;
import com.xuie.androiddemo.ui.activity.main.MainActivity;
import com.xuie.androiddemo.ui.fragment.AnimationFragment;
import com.xuie.androiddemo.ui.fragment.RecyclerViewFragment;
import com.xuie.androiddemo.ui.fragment.shots.ShotsFragment;
import com.xuie.androiddemo.ui.fragment.TestFragment;
import com.xuie.androiddemo.ui.fragment.TransitionsFragment;
import com.xuie.androiddemo.ui.fragment.ViewPagerIndicatorFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xuie on 16-4-10.
 */
public class MainPresenter extends BasePresenter<MainActivity> {
    private UserModel mUserModel;

    private User mCurrentUser;

    public MainPresenter() {
        mUserModel = UserModelImpl.getInstance();
    }

    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_test:
                getView().switchFragment(TestFragment.class.getName(), getString(R.string.test));
                break;
            case R.id.nav_new_frame:
                getView().switchFragment(ShotsFragment.class.getName(), getString(R.string.new_frame));
                break;
            case R.id.nav_transitions:
                getView().switchFragment(TransitionsFragment.class.getName(), getString(R.string.transitions));
                break;
            case R.id.nav_recycler_view:
                getView().switchFragment(RecyclerViewFragment.class.getName(), getString(R.string.recyclerview));
                break;
            case R.id.nav_animation:
                getView().switchFragment(AnimationFragment.class.getName(), getString(R.string.animation));
                break;
            case R.id.nav_indicator:
                getView().switchFragment(ViewPagerIndicatorFragment.class.getName(), getString(R.string.indicator));
                break;
        }
    }

    private String getString(@StringRes int id) {
        return getView().getResources().getString(id);
    }

    public void optionsItemSelected(int id) {
        if (id == R.id.action_day_night_yes) {
            getView().refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (id == R.id.action_day_night_no) {
            getView().refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (id == R.id.action_bottom_sheet_dialog) {
            getView().showBottomSheetDialog();
        } else if (id == R.id.action_weather) {
            getView().startWeather();
        }
    }


    public void loadUserInfoFromRealm() {
        mUserModel.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            if (user != null) {
                                mCurrentUser = user;
                                getView().loadUerInfo(mCurrentUser);
                                //从网络更新用户消息
                                loadUserInfoFromNet();
                            } else ToastUtil.Toast("你还没登录哟\n登陆后会更加精彩哟");
                        }
                        , throwable -> {
                            Logger.e("为什么又在这里出现问题了" + throwable.toString());
                            ToastUtil.Toast("出现了不可预计的错误...");
                        });
    }

    public void loadUserInfoFromNet() {
        mUserModel.getUseWithAccessToken(SPUtil.getAccessToken(App.getContext()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(user -> {
                    if (!UserUtil.enqual(user, mCurrentUser)) {
                        user.setAccessToken(mCurrentUser.getAccessToken());
                        Logger.d("更新USER数据成功");
                        mUserModel.saveUserToRealm(user);
                        return user;
                    }
                    return null;
                })
                .subscribe(user -> {
                    if (user != null) {
                        mCurrentUser = user;
                        getView().loadUerInfo(mCurrentUser);
                    }
                }, throwable -> {
                    Logger.e("为什么从网络上更新用户消息失败呀------>>>>" + throwable.toString());
                });
    }

    public void loadImageWithUrl(String url, ImageView imageView) {
        ImageModelImpl.getInstance().loadImageWithTargetView(url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imageView.setImageBitmap(resource);
            }
        });
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }


}
