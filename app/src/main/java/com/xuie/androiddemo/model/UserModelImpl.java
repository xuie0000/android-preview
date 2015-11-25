package com.xuie.androiddemo.model;

import android.util.Log;

import com.xuie.androiddemo.App;
import com.xuie.androiddemo.bean.AuthBody;
import com.xuie.androiddemo.bean.User;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.service.ServiceAPI;
import com.xuie.androiddemo.model.service.ServiceAPIModel;
import com.xuie.androiddemo.util.SPUtil;

import io.realm.Realm;
import rx.Observable;

public class UserModelImpl implements UserModel {
    private static final String TAG = "UserModelImpl";
    ServiceAPI mServiceAPI;

    private UserModelImpl() {
        mServiceAPI = ServiceAPIModel.provideServiceAPI(ServiceAPIModel.provideOkHttpClient());
    }

    public static UserModel getInstance() {
        return UserModelHolder.instance;
    }

    private final static class UserModelHolder {
        private final static UserModel instance = new UserModelImpl();
    }

    @Override public Observable<User> getCurrentUser() {
        final String accessToken = SPUtil.getAccessToken(App.getContext());
        if (!accessToken.equals(SPUtil.NOACESSTOKEN)) {
            return queryUserFromReaml(accessToken);
        }

        return Observable.just(null);
    }

    @Override public Observable deleteCurrentUser() {
        final String accessToken = SPUtil.getAccessToken(App.getContext());
        if (!accessToken.equals(SPUtil.NOACESSTOKEN)) {
            return queryUserFromReaml(accessToken)
                    .map(user -> {
                        Realm mReam = Realm.getDefaultInstance();
                        mReam.beginTransaction();
                        user.removeFromRealm();
                        mReam.commitTransaction();
                        return user;
                    });
        }

        return null;
    }

    @Override public Observable<User> queryUserFromReaml(String accessToken) {
        Log.d(TAG, "accessToken" + accessToken);
        return Observable.just(accessToken)
                .flatMap(s -> {
                    Realm mRealm = Realm.getDefaultInstance();
                    return mRealm.where(User.class)
                            .equalTo("accessToken", s)
                            .findAllAsync()
                            .asObservable()
//                            .flatMap(users ->
//                                    Observable.from(users));
                            .flatMap(Observable::from);
                });
    }

    @Override public Observable<String> Login2GetAccessToken(String authCode) {
        return mServiceAPI
                .getAccessToken(new AuthBody(authCode))
                .flatMap(accessToken -> Observable.just(accessToken.getAccess_token()));
    }

    @Override public Observable<User> getUseWithAccessToken(String accessToken) {
        return mServiceAPI.getUserWithAccessToken(accessToken);
    }

    @Override public Observable<User> setCurrentUser(User user) {
        return Observable.just(user)
                .map(user1 -> {
                    saveUserToReaml(user1);
                    return user1;
                });

    }

    @Override public void saveUserToReaml(User user) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();
        mRealm.close();
    }

    @Override public Observable<User> getOtherUser(String username) {
        return mServiceAPI.getUserInfo(username);
    }


}
