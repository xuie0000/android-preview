package com.xuie.androiddemo.ui.activity.shot;

import android.os.Handler;

import com.xuie.androiddemo.bean.dribbble.User;

import java.util.List;

public interface IShotActivity<T> {

    void showShots(List<T> list, int current_page);

    void uploadUserInfo(User user);

    Handler getHandler();
}
