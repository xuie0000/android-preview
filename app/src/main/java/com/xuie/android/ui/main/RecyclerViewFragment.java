package com.xuie.android.ui.main;

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

import com.xuie.android.R;
import com.xuie.android.bean.TextColor;
import com.xuie.android.ui.adapter.MarginDecoration;
import com.xuie.android.ui.adapter.RecyclerStaggeredViewAdapter;
import com.xuie.android.ui.adapter.RecyclerViewAdapter;

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
        // 记录切换前第一个可视视图位置
        int firstPosition = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            firstPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(firstPositions);
            firstPosition = findMax(firstPositions);
        }

        recyclerView.addItemDecoration(new MarginDecoration());

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
        recyclerView.scrollToPosition(firstPosition);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
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
