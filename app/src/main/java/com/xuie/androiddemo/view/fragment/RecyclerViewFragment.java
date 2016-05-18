/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.xuie.androiddemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.TextPicture;
import com.xuie.androiddemo.view.adapter.RecyclerStaggeredViewAdapter;
import com.xuie.androiddemo.view.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class RecyclerViewFragment extends Fragment {
    static final String KEY_LAYOUT_MANAGER = "layoutManager";
    static final int SPAN_COUNT = 2;
    static final int DATA_COUNT = 60;

    /**
     * 线性布局/Grid布局
     */
    enum LayoutManagerType {
        GRID_VER_MANAGER,
        LINEAR_VER_MANAGER,
        LINEAR_HOR_MANAGER,
    }

    /**
     * 单一布局/双布局Item
     */
    enum ItemType {
        SINGLE,
        STAGGERED,
    }

    int[] colors = {android.R.color.holo_blue_dark,
            android.R.color.holo_blue_bright,
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_dark,
            android.R.color.holo_purple,
            android.R.color.holo_red_dark,
            android.R.color.holo_red_light};

    LayoutManagerType mCurrentLayoutManagerType;
    ItemType mItemType;
    RecyclerViewAdapter mAdapter;
    RecyclerStaggeredViewAdapter mStaggeredAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<TextPicture> textPictures = new ArrayList<>();

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int per = -1, cur;
        Random r = new Random();
        for (int i = 0; i < DATA_COUNT; i++) {
            do {
                cur = r.nextInt(8);
            } while (cur == per);
            per = cur;
            textPictures.add(new TextPicture("This is element #" + i, colors[cur]));
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        ButterKnife.bind(this, rootView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER;
        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager();

        mAdapter = new RecyclerViewAdapter(textPictures);
        mStaggeredAdapter = new RecyclerStaggeredViewAdapter(textPictures);
        mItemType = ItemType.SINGLE;
        addAdapter();

        return rootView;
    }

    @OnClick({R.id.linear_layout_rb, R.id.grid_layout_rb, R.id.linear_hor_rb,
            R.id.single_rb, R.id.stagger_rb})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_layout_rb:
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER;
                setRecyclerViewLayoutManager();
                break;
            case R.id.grid_layout_rb:
                mCurrentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER;
                setRecyclerViewLayoutManager();
                break;
            case R.id.linear_hor_rb:
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_HOR_MANAGER;
                setRecyclerViewLayoutManager();
                break;
            case R.id.single_rb:
                mItemType = ItemType.SINGLE;
                addAdapter();
                break;
            case R.id.stagger_rb:
                mItemType = ItemType.STAGGERED;
                addAdapter();
                break;
        }
    }

    private void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        switch (mCurrentLayoutManagerType) {
            case GRID_VER_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                break;
            case LINEAR_VER_MANAGER:
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                break;
            case LINEAR_HOR_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void addAdapter() {
        if (mItemType == ItemType.SINGLE) {
            // 1. 默认
//            mRecyclerView.setAdapter(mAdapter);
            // 2. 添加动画
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            scaleAdapter.setFirstOnly(false);
            scaleAdapter.setInterpolator(new OvershootInterpolator());
            mRecyclerView.setAdapter(scaleAdapter);
        } else if (mItemType == ItemType.STAGGERED) {
            // 3. 默认添加双布局
//            mRecyclerView.setAdapter(mStaggeredAdapter);
            // 4. 添加双布局的动画
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mStaggeredAdapter);
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            scaleAdapter.setFirstOnly(false);
            scaleAdapter.setInterpolator(new OvershootInterpolator());
            mRecyclerView.setAdapter(scaleAdapter);
        }
    }
}
