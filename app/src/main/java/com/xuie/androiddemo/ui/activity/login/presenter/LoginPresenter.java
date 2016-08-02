package com.xuie.androiddemo.ui.activity.login.presenter;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.model.service.DribbbleAPI;
import com.xuie.androiddemo.model.service.ServiceGenerator;
import com.xuie.androiddemo.presenter.BasePresenter;
import com.xuie.androiddemo.ui.activity.login.LoginActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginActivity> {
    DribbbleAPI dribbbleAPI;
    String accessToken;
    UserModel userModel;

    public LoginPresenter() {
        dribbbleAPI = ServiceGenerator.createService(DribbbleAPI.class);
        userModel = UserModelImpl.getInstance();
    }

    public void getAccessToken(String client_id, String client_secret, String code, String redirect_uri) {

        userModel.Login2GetAccessToken(client_id, client_secret, code, redirect_uri)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Logger.d("s-:" + s);
//                    System.out.println("s::::" + s);
                }, throwable -> Logger.e(throwable.getMessage()));

//        userModel.Login2GetAccessToken(authCode)
//                .flatMap(s -> {
//                    accessToken = s;
//                    Utils.putAccessToken(App.getContext(), s);
//                    return userModel.getUseWithAccessToken(s);
//                })
//                .subscribeOn(Schedulers.newThread())
//                .map(user -> {
//                    user.setAccessToken(accessToken);
//                    userModel.saveUserToRealm(user);
//                    return user;
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(user -> {
////                    getView().setResult(Activity.RESULT_OK);
////                    getView().finish();
//                }, throwable -> Logger.e(throwable.getMessage()));
    }
}
