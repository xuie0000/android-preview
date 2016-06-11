package com.xuie.androiddemo.model.IModel;

import com.xuie.androiddemo.bean.dribbble.User;

import rx.Observable;

public interface UserModel {

    Observable<User> getCurrentUser();

    Observable deleteCurrentUser();

    Observable<User> queryUserFromRealm(String accessToken);

    Observable<String> Login2GetAccessToken(String oauthCode);

    Observable<User> getUseWithAccessToken(String accessToken);

    Observable<User> setCurrentUser(User user);

    void saveUserToRealm(User user);

    Observable<User> getOtherUser(String username);
}
