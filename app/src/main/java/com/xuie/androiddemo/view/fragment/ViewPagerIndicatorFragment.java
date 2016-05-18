package com.xuie.androiddemo.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.widget.IndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * http://blog.csdn.net/jxxfzgy/article/details/43813275
 */
public class ViewPagerIndicatorFragment extends Fragment {
    final static String TAG = ViewPagerIndicatorFragment.class.getSimpleName();

    @BindView(R.id.id_view_pager) ViewPager viewPager;
    @BindView(R.id.id_indicator) IndicatorView indicator;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_indicator, container, false);
        ButterKnife.bind(this, view);

        Log.d(TAG, "onCreateView");
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        indicator.setViewPager(viewPager);
        return view;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return MyFragment.newInstance(position);
        }

        @Override public int getCount() {
            return 2;
        }
    }

    public static class MyFragment extends Fragment {
        public static final String ID = "id";

        int[] imgIds = {R.mipmap.t_01, R.mipmap.t_02};
        int id;

        public static MyFragment newInstance(int id) {
            Bundle args = new Bundle();
            args.putInt(ID, id);
            MyFragment fragment = new MyFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                this.id = getArguments().getInt(ID);
            } else {
                throw new IllegalArgumentException("error argument");
            }
        }

        @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imgIds[id]);
            return imageView;
        }
    }
}
