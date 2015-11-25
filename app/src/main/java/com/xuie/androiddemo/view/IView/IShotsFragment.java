package com.xuie.androiddemo.view.IView;

import android.os.Handler;

import com.xuie.androiddemo.bean.User;

import java.util.List;

public interface IShotsFragment<T> {

    void showShots(List<T> list, int current_page);

    void showProgress();

    void closeProgress();

    void uploadUserInfo(User user);

    Handler getHandler();
}
