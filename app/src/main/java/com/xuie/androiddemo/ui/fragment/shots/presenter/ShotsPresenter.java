package com.xuie.androiddemo.ui.fragment.shots.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.bean.dribbble.Shot;
import com.xuie.androiddemo.bean.dribbble.User;
import com.xuie.androiddemo.model.IModel.ShotModel;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.ImageModelImpl;
import com.xuie.androiddemo.model.ShotModelImpl;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.presenter.BasePresenter;
import com.xuie.androiddemo.util.Utils;
import com.xuie.androiddemo.util.ToastUtils;
import com.xuie.androiddemo.util.UserUtils;
import com.xuie.androiddemo.ui.fragment.shots.ShotsFragment;
import com.xuie.androiddemo.widget.CircleImageView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotsPresenter extends BasePresenter<ShotsFragment> {
    private ShotModel shotModel;
    private UserModel userModel;
    private static User mCurrentUser = null;
    public static int page = 2;
    int per_page = 10;
    Shot mShot;
    boolean realm_is_null = false;

    public ShotsPresenter() {
        userModel = UserModelImpl.getInstance();
        shotModel = ShotModelImpl.getInstance();
    }

    public void loadDataFromRealm() {
        shotModel.loadShots2Realm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shots -> {
                    Logger.d("load form realm shots size: " + shots.size() + "\n ?content : " + shots.toString());
                    if (shots.size() == 0) realm_is_null = true;
                    else mShot = shots.get(0);
                    getView().showShots(shots, 1);
                }, throwable -> Logger.e(throwable.getMessage()));

        requestNewDate();
    }

    public void loadShotsFromServer(int page, int per_page, boolean isShow) {
        shotModel.getShotsFromServer(page, per_page)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(() -> getView().getHandler().post(() -> {
                    if (isShow) showProgress();
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shots -> {
                    closeProgress();

                    if (mShot != null && (mShot.getId().equals(shots.get(0).getId()) && isShow)) {
                        ToastUtils.Toast("已经是最新的数据了");
                    } else if (mShot != null && (!(mShot.getId().equals(shots.get(0).getId())) && isShow)) {
                        shotModel.clearShotsToRealm();
                        shotModel.saveShotsToRealm(shots);
                        getView().showShots(shots, page);
                        mShot = shots.get(0);
                        Logger.d("首页有更新重新保存数据");
                    } else if (realm_is_null) {
                        shotModel.clearShotsToRealm();
                        shotModel.saveShotsToRealm(shots);
                        getView().showShots(shots, page);
                        realm_is_null = false;
                        mShot = shots.get(0);
                        Logger.d("第一次成功请求后保存数据");
                    } else {
                        Logger.e("按道理来说不可能到这里来");
                        getView().showShots(shots, page);
                    }
                }, throwable -> {
                    closeProgress();
                    ToastUtils.Toast("网络刷新出现了错误");
                    Logger.e(throwable.getMessage());
                });
    }

    @Override public void OnViewResume() {
        super.OnViewResume();
    }

    @Override public void onViewDestroy() {
        super.onViewDestroy();
        shotModel.closeSomeThing();
    }

    public void updateUserInfo() {
        userModel.getUseWithAccessToken(Utils.getAccessToken(App.getContext()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(user -> {
                    if (!UserUtils.equals(user, mCurrentUser)) {
                        user.setAccessToken(mCurrentUser.getAccessToken());
                        Logger.d("更新USER数据成功");
                        userModel.saveUserToRealm(user);
                        return user;
                    }
                    return null;
                })
                .subscribe(user -> {
                    if (user != null) {
                        mCurrentUser = user;
                        getView().uploadUserInfo(mCurrentUser);
                    }
                }, throwable -> Logger.e(throwable.getMessage()));
    }

    public void requestDate() {
        Logger.d("上拉加载数据中...");
        loadShotsFromServer(page, per_page, false);
        page++;
    }

    public void requestNewDate() {
        loadShotsFromServer(ShotModelImpl.PAGE, ShotModelImpl.PER_PAGE, true);
    }

    public void showUserInfo() {
        userModel.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if (user != null) {
                        mCurrentUser = user;
                        getView().uploadUserInfo(mCurrentUser);
                        //从网络更新用户消息
                        updateUserInfo();
                    } else ToastUtils.Toast("你还没登录哟\n登陆后会更加精彩哟");
                }, throwable -> Logger.e(throwable.getMessage()));
    }

    public void loadImageWithurl(String url, CircleImageView imageView) {
        ImageModelImpl.getInstance().loadImageWithTargetView(url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imageView.setImageBitmap(resource);
            }
        });
    }

    private void showProgress() {
        getView().showProgress();
    }

    private void closeProgress() {
        getView().closeProgress();
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void loadImageWithurl(String url, ImageView imageView) {
        ImageModelImpl.getInstance().loadImage(url, imageView);
    }
}
