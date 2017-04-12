package com.xuie.android.ui.recyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
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
import com.xuie.android.util.PreferenceUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "RecyclerViewFragment";
    public static final String KEY_LAYOUT_MANAGER = "layoutManager";
    public static final int SPAN_COUNT = 2;
    public static final int DATA_COUNT = 60;

    private enum LayoutManagerType {
        GRID_VER_MANAGER,
        LINEAR_VER_MANAGER,
        LINEAR_HOR_MANAGER,
    }

    private enum ItemType {
        SINGLE,
        STAGGERED,
    }

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private LayoutManagerType currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER;
    private ItemType currentItemType = ItemType.STAGGERED;
    private ColorAdapter colorAdapter;
    private ColorAdapter colorStaggeredAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initTextColor();
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

        colorAdapter = new ColorAdapter(getContext());
        colorStaggeredAdapter = new ColorStaggeredAdapter(getContext());
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

    private void initTextColor() {
        if (PreferenceUtils.getBoolean("run_data", false)) {
            return;
        }

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
//            textPictures.add(new TextColor("This is element #" + i, colors[cur]));
            ContentValues contentValues = new ContentValues();
            contentValues.put(ColorContract.ColorEntry.COLUMN_NAME, "element #" + cur);
            contentValues.put(ColorContract.ColorEntry.COLUMN_COLOR, colors[cur]);
            getActivity().getContentResolver().insert(ColorContract.ColorEntry.CONTENT_URI, contentValues);
        }

        PreferenceUtils.setBoolean("run_data", true);
    }

}
