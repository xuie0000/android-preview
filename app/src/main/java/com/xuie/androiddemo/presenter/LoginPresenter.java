package com.xuie.androiddemo.presenter;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.bean.User;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.model.service.ServiceAPI;
import com.xuie.androiddemo.model.service.ServiceAPIModel;
import com.xuie.androiddemo.util.SPUtil;
import com.xuie.androiddemo.view.activity.LoginActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginActivity> {
    ServiceAPI serviceAPI;
    private String accessToken;
    private UserModel mUserModel;

    @Override
    public void OnViewResume() {
        super.OnViewResume();
        serviceAPI = ServiceAPIModel.provideServiceAPI(ServiceAPIModel.provideOkHttpClient());
        mUserModel = UserModelImpl.getInstance();
    }

    public void getAccessToken(final String authCode) {
        mUserModel.Login2GetAccessToken(authCode)
                .flatMap(new Func1<String, Observable<User>>() {
                    @Override
                    public Observable<User> call(String s) {
                        accessToken = s;
                        SPUtil.putAccesToken(App.getContext(), s);
                        return mUserModel.getUseWithAccessToken(s);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<User, User>() {
                    @Override
                    public User call(User user) {
                        user.setAccessToken(accessToken);
                        mUserModel.saveUserToReaml(user);
                        return user;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
//                        getView().setResult(Activity.RESULT_OK);
//                        getView().finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("又是在这里出现了问题呀----->" + throwable.toString());
                    }
                });
    }
}
