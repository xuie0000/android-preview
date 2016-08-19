package com.xuie.androiddemo.ui.activity.main;

/**
 * Created by xuie on 16-4-10.
 */
public interface IMainActivity {
    void switchFragment(String fragName, String fragTitle);
    void refreshDelegateMode(int mode);
    void showBottomSheetDialog();
    void startWeather();
    void startPalette();
}
