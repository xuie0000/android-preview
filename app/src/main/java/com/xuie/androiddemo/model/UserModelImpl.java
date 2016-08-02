package com.xuie.androiddemo.model;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.bean.dribbble.AuthBody;
import com.xuie.androiddemo.bean.dribbble.User;
import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.service.DribbbleAPI;
import com.xuie.androiddemo.model.service.ServiceGenerator;
import com.xuie.androiddemo.util.Utils;

import io.realm.Realm;
import rx.Observable;

public class UserModelImpl implements UserModel {
    static UserModel instance;
    DribbbleAPI mServiceAPI;

    private UserModelImpl() {
        mServiceAPI = ServiceGenerator.createService(DribbbleAPI.class);
    }

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModelImpl.class) {
                instance = new UserModelImpl();
            }
        }
        return instance;
    }

    @Override public Observable<User> getCurrentUser() {
        final String accessToken = Utils.getAccessToken(App.getContext());
        if (!accessToken.equals(Utils.NOACESSTOKEN)) {
            return queryUserFromRealm(accessToken);
        }

        return Observable.just(null);
    }

    @Override public Observable deleteCurrentUser() {
        final String accessToken = Utils.getAccessToken(App.getContext());
        if (!accessToken.equals(Utils.NOACESSTOKEN)) {
            return queryUserFromRealm(accessToken)
                    .map(user -> {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        return user;
                    });
        }

        return null;
    }

    @Override public Observable<User> queryUserFromRealm(String accessToken) {
        Logger.d("accessToken" + accessToken);
        return Observable.just(accessToken)
                .flatMap(s -> {
                    Realm mRealm = Realm.getDefaultInstance();
                    return mRealm.where(User.class)
                            .equalTo("accessToken", s)
                            .findAllAsync()
                            .asObservable()
                            .flatMap(Observable::from);
                });
    }

    @Override public Observable<String> Login2GetAccessToken(String authCode) {
        return mServiceAPI
                .getAccessToken(new AuthBody(authCode))
                .flatMap(accessToken -> Observable.just(accessToken.getAccess_token()));
    }

    @Override public Observable<String> Login2GetAccessToken(String client_id, String client_secret, String code, String redirect_uri) {
        return mServiceAPI.getDrToken(client_id, client_secret, code, redirect_uri);
    }

    @Override public Observable<User> getUseWithAccessToken(String accessToken) {
        return mServiceAPI.getUserWithAccessToken(accessToken);
    }

    @Override public Observable<User> setCurrentUser(User user) {
        return Observable.just(user)
                .map(user1 -> {
                    saveUserToRealm(user1);
                    return user1;
                });

    }

    @Override public void saveUserToRealm(User user) {
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
