package com.xuie.androiddemo.view.IView;

import com.xuie.androiddemo.bean.User;

/**
 * Created by xuie on 16-4-10.
 */
public interface IMainActivity {
    void switchFragment(String fragName, String fragTitle);
    void refreshDelegateMode(int mode);
    void showBottomSheetDialog();
    void loadUerInfo(User user);
}
