package com.xuie.androiddemo.ui.fragment.shots;

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
