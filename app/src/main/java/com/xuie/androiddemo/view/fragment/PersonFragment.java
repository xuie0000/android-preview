package com.xuie.androiddemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.User;
import com.xuie.androiddemo.presenter.PersonPresenter;
import com.xuie.androiddemo.view.IView.IPersonFragment;
import com.xuie.androiddemo.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonFragment extends BaseFragment<PersonFragment, PersonPresenter> implements IPersonFragment {

    @Bind(R.id.person_toolbar) Toolbar mToolbar;
    @Bind(R.id.img_user_avatar) CircleImageView mUserAvater;
    @Bind(R.id.person_tabLayout) TabLayout mTabLayout;
    @Bind(R.id.person_appbar) AppBarLayout mAppBarLayout;
    @Bind(R.id.person_viewpager) ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected PersonPresenter createPresenter() {
        return new PersonPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showCurrentUser(User user) {

    }

    @Override
    public void showOtherUser(User user) {

    }
}
