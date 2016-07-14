package com.xuie.androiddemo.ui.activity.main;

import com.xuie.androiddemo.bean.dribbble.User;

/**
 * Created by xuie on 16-4-10.
 */
public interface IMainActivity {
    void switchFragment(String fragName, String fragTitle);
    void refreshDelegateMode(int mode);
    void showBottomSheetDialog();
    void loadUerInfo(User user);
    void startWeather();
}
