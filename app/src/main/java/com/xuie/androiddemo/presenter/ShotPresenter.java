package com.xuie.androiddemo.presenter;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.bean.Shot;
import com.xuie.androiddemo.bean.User;
import com.xuie.androiddemo.model.IModel.ShotModel;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.ImageModelImpl;
import com.xuie.androiddemo.model.ShotModelImpl;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.util.SPUtil;
import com.xuie.androiddemo.util.ToastUtil;
import com.xuie.androiddemo.util.UserUtil;
import com.xuie.androiddemo.view.activity.ShotActivity;
import com.xuie.androiddemo.widget.CircleImageView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ShotPresenter extends BasePresenter<ShotActivity> {
    private static final String TAG = "ShotsPresenter";
    private ShotModel shotModel;
    private UserModel userModel;
    private static User mCurrentUser = null;
    public static int page = 2;
    int per_page = 10;
    Shot mShot;
    boolean realm_is_null = false;

    public ShotPresenter() {
        userModel = UserModelImpl.getInstance();
        shotModel = ShotModelImpl.getInstance();
    }

    public void loadDataFromReaml() {
        shotModel.loadShots().subscribe(new Action1<List<Shot>>() {
            @Override
            public void call(List<Shot> shots) {
                Log.d(TAG, "load form realm shosts.size()------>>>>>" + shots.size());
                if (shots.size() == 0) realm_is_null = true;
                else mShot = shots.get(0);
                getView().showShots(shots, 1);
            }
        }, throwable -> Log.e(TAG, "从Realm中加载数据出错----->>>>" + throwable.toString()));

        requestNewDate();
    }

    public void loadShotsFromServer(int page, int per_page, boolean isShow) {
        shotModel.getShotsFromServer(page, per_page)
                .subscribeOn(Schedulers.newThread())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        getView().getHandler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (isShow) showProgress();
//                            }
//                        });
//                    }
//                })
                .doOnSubscribe(() -> getView().getHandler().post(() -> {
                    if (isShow) showProgress();
                }))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shots -> {
                    closeProgress();
                    Logger.d("这里一共有" + shots.size()
                            + "个数据\n" + "这里的page是：" + page
                            + "\n这里的realm_is_null" + realm_is_null
                            + "\n这里的isShow" + isShow
                            + "\nmShot---->>>" + mShot.getId().toString()
                            + "\nshot----->>>>>" + shots.get(0).getId().toString());
                    if (mShot != null && (mShot.getId().equals(shots.get(0).getId()) && isShow)) {
                        ToastUtil.Toast("已经是最新的数据了");
                    } else if (mShot != null && (!(mShot.getId().equals(shots.get(0).getId())) && isShow)) {
                        shotModel.clearShotsToRealm();
                        shotModel.saveShotsToRealm(shots);
                        getView().showShots(shots, page);
                        mShot = shots.get(0);
                        Logger.d("首页有更新重新保存数据");
                    } else if (realm_is_null) {
                        shotModel.saveShotsToRealm(shots);
                        getView().showShots(shots, page);
                        realm_is_null = false;
                        mShot = shots.get(0);
                        Logger.d("第一次成功请求后保存数据");
                    } else {
                        Logger.e("按道理来说不可能到这里来呀------_--");
                        getView().showShots(shots, page);
                    }
                }, throwable -> {
                    closeProgress();
                    ToastUtil.Toast("网络刷新出现了错误");
                    Log.e(TAG, "从server中加载数据出错----->>>>" + throwable.toString());
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
        userModel.getUseWithAccessToken(SPUtil.getAccessToken(App.getContext()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<User, User>() {
                    @Override
                    public User call(User user) {
                        if (!UserUtil.enqual(user, mCurrentUser)) {
                            user.setAccessToken(mCurrentUser.getAccessToken());
                            Log.d(TAG, "更新USER数据成功");
                            userModel.saveUserToReaml(user);
                            return user;
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        if (user != null) {
                            mCurrentUser = user;
                            getView().uploadUserInfo(mCurrentUser);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "为什么从网络上更新用户消息失败呀------>>>>" + throwable.toString());
                    }
                });
    }

    public void requestDate() {
        Log.d(TAG, "上拉加载数据中...");
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
                            } else ToastUtil.Toast("你还没登录哟\n登陆后会更加精彩哟");
                        }
                        , throwable -> {
                            Log.e(TAG, "为什么又在这里出现问题了" + throwable.toString());
                            ToastUtil.Toast("出现了不可预计的错误...");
                        });
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
//        getView().showProgress();
    }

    private void closeProgress() {
//        getView().closeProgress();
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void loadImageWithurl(String url, ImageView imageView) {
        ImageModelImpl.getInstance().loadImage(url, imageView);
    }
}
