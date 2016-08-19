package com.xuie.androiddemo.ui.activity.main.presenter;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDelegate;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.presenter.BasePresenter;
import com.xuie.androiddemo.ui.activity.main.MainActivity;
import com.xuie.androiddemo.ui.fragment.AnimationFragment;
import com.xuie.androiddemo.ui.fragment.RecyclerViewFragment;
import com.xuie.androiddemo.ui.fragment.TestFragment;
import com.xuie.androiddemo.ui.fragment.TransitionsFragment;

/**
 * Created by xuie on 16-4-10.
 */
public class MainPresenter extends BasePresenter<MainActivity> {

    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_test:
                getView().switchFragment(TestFragment.class.getName(), getString(R.string.test));
                break;
            case R.id.nav_transitions:
                getView().switchFragment(TransitionsFragment.class.getName(), getString(R.string.transitions));
                break;
            case R.id.nav_recycler_view:
                getView().switchFragment(RecyclerViewFragment.class.getName(), getString(R.string.recycler_view));
                break;
            case R.id.nav_animation:
                getView().switchFragment(AnimationFragment.class.getName(), getString(R.string.animation));
                break;
            case R.id.nav_palette:
                getView().startPalette();
                break;
        }
    }

    private String getString(@StringRes int id) {
        return getView().getResources().getString(id);
    }

    public void optionsItemSelected(int id) {
        if (id == R.id.action_day_night_yes) {
            getView().refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (id == R.id.action_day_night_no) {
            getView().refreshDelegateMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (id == R.id.action_bottom_sheet_dialog) {
            getView().showBottomSheetDialog();
        } else if (id == R.id.action_weather) {
            getView().startWeather();
        }
    }


}
