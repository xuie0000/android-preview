package com.xuie.androiddemo.presenter;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.model.service.ServiceAPI;
import com.xuie.androiddemo.model.service.ServiceGenerator;
import com.xuie.androiddemo.util.SPUtil;
import com.xuie.androiddemo.view.activity.LoginActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginActivity> {
    ServiceAPI mServiceAPI;
    private String accessToken;
    private UserModel mUserModel;

    @Override
    public void OnViewResume() {
        super.OnViewResume();
        mServiceAPI = ServiceGenerator.createService(ServiceAPI.class);
        mUserModel = UserModelImpl.getInstance();
    }

    public void getAccessToken(final String authCode) {
        mUserModel.Login2GetAccessToken(authCode)
                .flatMap(s -> {
                    accessToken = s;
                    SPUtil.putAccesToken(App.getContext(), s);
                    return mUserModel.getUseWithAccessToken(s);
                })
                .subscribeOn(Schedulers.newThread())
                .map(user -> {
                    user.setAccessToken(accessToken);
                    mUserModel.saveUserToRealm(user);
                    return user;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
//                    getView().setResult(Activity.RESULT_OK);
//                    getView().finish();
                }, throwable -> Logger.e(throwable.getMessage()));
    }
}
