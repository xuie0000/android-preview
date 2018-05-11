package com.xuie.android.ui.recycler.axis;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * 这是一个时间轴的DEMO，添加的 {@link RecyclerView.ItemDecoration}样例
 * @author xuie
 */
public class AxisFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_axis, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setAdapter(new AxisAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new AxisItemDecoration());


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
