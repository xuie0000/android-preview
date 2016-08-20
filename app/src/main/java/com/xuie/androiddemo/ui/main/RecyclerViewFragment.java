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

package com.xuie.androiddemo.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.TextColor;
import com.xuie.androiddemo.ui.adapter.RecyclerStaggeredViewAdapter;
import com.xuie.androiddemo.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewFragment extends Fragment {
    public static final String KEY_LAYOUT_MANAGER = "layoutManager";
    public static final int SPAN_COUNT = 2;
    public static final int DATA_COUNT = 60;

    enum LayoutManagerType {
        GRID_VER_MANAGER,
        LINEAR_VER_MANAGER,
        LINEAR_HOR_MANAGER,
    }

    enum ItemType {
        SINGLE,
        STAGGERED,
    }

    @BindView(R.id.linear_layout) RadioButton linearLayout;
    @BindView(R.id.grid_layout) RadioButton gridLayout;
    @BindView(R.id.linear_horizontal) RadioButton linearHorizontal;
    @BindView(R.id.single) RadioButton single;
    @BindView(R.id.stagger) RadioButton stagger;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    LayoutManagerType currentLayoutManagerType;
    ItemType currentItemType;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerStaggeredViewAdapter recyclerStaggeredViewAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<TextColor> textPictures = new ArrayList<>();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTextColor();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER;
        if (savedInstanceState != null) {
            currentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(currentLayoutManagerType);

        recyclerViewAdapter = new RecyclerViewAdapter(textPictures);
        recyclerStaggeredViewAdapter = new RecyclerStaggeredViewAdapter(textPictures);
        currentItemType = ItemType.SINGLE;
        refreshAdapter(recyclerViewAdapter);

        linearLayout.setOnClickListener(v -> {
            if (currentLayoutManagerType != LayoutManagerType.LINEAR_VER_MANAGER) {
                currentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER;
                setRecyclerViewLayoutManager(currentLayoutManagerType);
            }
        });

        gridLayout.setOnClickListener(v -> {
            if (currentLayoutManagerType != LayoutManagerType.GRID_VER_MANAGER) {
                currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER;
                setRecyclerViewLayoutManager(currentLayoutManagerType);
            }
        });

        linearHorizontal.setOnClickListener(v -> {
            if (currentLayoutManagerType != LayoutManagerType.LINEAR_HOR_MANAGER) {
                currentLayoutManagerType = LayoutManagerType.LINEAR_HOR_MANAGER;
                setRecyclerViewLayoutManager(currentLayoutManagerType);
            }
        });

        single.setOnClickListener(v -> {
            if (currentItemType != ItemType.SINGLE) {
                currentItemType = ItemType.SINGLE;
                refreshAdapter(recyclerViewAdapter);
            }
        });

        stagger.setOnClickListener(v -> {
            if (currentItemType != ItemType.STAGGERED) {
                currentItemType = ItemType.STAGGERED;
                refreshAdapter(recyclerStaggeredViewAdapter);
            }
        });
    }

    private void setRecyclerViewLayoutManager(LayoutManagerType type) {
        int scrollPosition = 0;
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        switch (type) {
            case GRID_VER_MANAGER:
                if (currentItemType != ItemType.STAGGERED) {
                    layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                } else {
                    layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                }
                break;
            case LINEAR_VER_MANAGER:
            default:
                layoutManager = new LinearLayoutManager(getActivity());
                break;
            case LINEAR_HOR_MANAGER:
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void refreshAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private void initTextColor() {
        int[] colors = {
                ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark),
                ContextCompat.getColor(getActivity(), android.R.color.holo_blue_bright),
                ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light),
                ContextCompat.getColor(getActivity(), android.R.color.holo_green_light),
                ContextCompat.getColor(getActivity(), android.R.color.holo_orange_dark),
                ContextCompat.getColor(getActivity(), android.R.color.holo_purple),
                ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark),
                ContextCompat.getColor(getActivity(), android.R.color.holo_red_light),
        };


        int per = -1, cur;
        Random r = new Random();
        for (int i = 0; i < DATA_COUNT; i++) {
            do {
                cur = r.nextInt(8);
            } while (cur == per);
            per = cur;
            textPictures.add(new TextColor("This is element #" + i, colors[cur]));
        }
    }

}
