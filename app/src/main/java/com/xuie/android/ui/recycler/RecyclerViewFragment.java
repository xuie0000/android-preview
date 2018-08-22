package com.xuie.android.ui.recycler;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.android.R;
import com.xuie.android.ui.recycler.axis.AxisFragment;
import com.xuie.android.ui.recycler.diffutil.DiffUtilFragment;
import com.xuie.android.ui.recycler.discrete.DiscreteFragment;
import com.xuie.android.ui.recycler.normal.NormalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class RecyclerViewFragment extends Fragment {

    /**
     * The Recycler view.
     */
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    /**
     * The Unbinder.
     */
    Unbinder unbinder;
    /**
     * The Frame layout.
     */
    @BindView(R.id.frame_layout) FrameLayout frameLayout;

    private List<Member> members;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        members = new ArrayList<>();
        members.add(new Member("样例", NormalFragment.class.getName()));
        members.add(new Member("DiffUtil", DiffUtilFragment.class.getName()));
        members.add(new Member("ItemDecoration(时间轴)", AxisFragment.class.getName()));
        members.add(new Member("滑动缩放", DiscreteFragment.class.getName()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, v);

        fragmentManager = getChildFragmentManager();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter listAdapter = new ListAdapter(android.R.layout.simple_list_item_1);
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            try {
                Fragment fragment = (Fragment) Class.forName(((Member) adapter.getItem(position)).fragment).newInstance();
                transaction.replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            } catch (java.lang.InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        recyclerView.setAdapter(listAdapter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class ListAdapter extends BaseQuickAdapter<Member, BaseViewHolder> {
        /**
         * Instantiates a new List adapter.
         *
         * @param layoutResId the layout res id
         */
        ListAdapter(@LayoutRes int layoutResId) {
            super(layoutResId, members);
        }

        @Override
        protected void convert(BaseViewHolder helper, Member item) {
            helper.setText(android.R.id.text1, item.name);
        }
    }

    private class Member {
        /**
         * The Name.
         */
        public String name;
        /**
         * The Fragment.
         */
        public String fragment;

        /**
         * Instantiates a new Member.
         *
         * @param name     the name
         * @param fragment the fragment
         */
        Member(String name, String fragment) {
            this.name = name;
            this.fragment = fragment;
        }
    }

}
