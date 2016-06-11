package com.xuie.androiddemo.view.fragment.IView;

import android.os.Handler;

import com.xuie.androiddemo.bean.dribbble.User;

import java.util.List;

public interface IShotsFragment<T> {

    void showShots(List<T> list, int current_page);

    void showProgress();

    void closeProgress();

    void uploadUserInfo(User user);

    Handler getHandler();
}
