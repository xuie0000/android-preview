package com.xuie.android.ui.recyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.android.R;
import com.xuie.android.provider.ColorContract;
import com.xuie.android.ui.adapter.MarginDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "RecyclerViewFragment";
    public static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private enum LayoutManagerType {
        GRID_VER_MANAGER,
        LINEAR_VER_MANAGER,
        LINEAR_HOR_MANAGER,
    }

    private enum ItemType {
        SINGLE,
        STAGGERED,
    }

    public static final int SPAN_COUNT = 2;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private LayoutManagerType currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER;
    private ItemType currentItemType = ItemType.STAGGERED;
    private ColorAdapter colorAdapter;
    private ColorAdapter colorStaggeredAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            currentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(currentLayoutManagerType);
        recyclerView.addItemDecoration(new MarginDecoration());

        colorAdapter = new ColorAdapter(getContext(), null);
        colorStaggeredAdapter = new ColorStaggeredAdapter(getContext(), null);
        recyclerView.setAdapter(colorStaggeredAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(Menu.NONE, R.id.linear_v_single, Menu.NONE, "Linear & Vertical & Single").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(Menu.NONE, R.id.linear_h_single, Menu.NONE, "Linear & Horizontal & Single").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(Menu.NONE, R.id.grid_v_single, Menu.NONE, "Grid & Vertical & Single").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(Menu.NONE, R.id.grid_h_stagger, Menu.NONE, "Grid & Horizontal & Stagger").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linear_v_single:
                currentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER;
                currentItemType = ItemType.SINGLE;
                break;
            case R.id.linear_h_single:
                currentLayoutManagerType = LayoutManagerType.LINEAR_HOR_MANAGER;
                currentItemType = ItemType.SINGLE;
                break;
            case R.id.grid_v_single:
                currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER;
                currentItemType = ItemType.SINGLE;
                break;
            case R.id.grid_h_stagger:
                currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER;
                currentItemType = ItemType.STAGGERED;
                break;
        }
        setRecyclerViewLayoutManager(currentLayoutManagerType);
        if (currentItemType == ItemType.SINGLE) {
            refreshAdapter(colorAdapter);
        } else if (currentItemType == ItemType.STAGGERED) {
            refreshAdapter(colorStaggeredAdapter);
        }
        return super.onOptionsItemSelected(item);
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
        recyclerView.setNestedScrollingEnabled(false);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new CursorLoader(
                        getContext(),
                        ColorContract.ColorEntry.CONTENT_URI,
                        ColorAdapter.MOVIE_COLUMNS,
                        null,
                        null,
                        null
                );
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 0:
                colorAdapter.swapCursor(data);
                colorStaggeredAdapter.swapCursor(data);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case 0:
                colorAdapter.swapCursor(null);
                colorStaggeredAdapter.swapCursor(null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }
    }

    private void refreshAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}
