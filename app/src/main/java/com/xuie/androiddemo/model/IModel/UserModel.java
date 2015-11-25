package com.xuie.androiddemo.model.IModel;

import com.xuie.androiddemo.bean.User;

import rx.Observable;

public interface UserModel {

    Observable<User> getCurrentUser();

    Observable deleteCurrentUser();

    Observable<User> queryUserFromReaml(String accessToken);

    Observable<String> Login2GetAccessToken(String oauthCode);

    Observable<User> getUseWithAccessToken(String accessToken);

    Observable<User> setCurrentUser(User user);

    void saveUserToReaml(User user);

    Observable<User> getOtherUser(String username);
}
