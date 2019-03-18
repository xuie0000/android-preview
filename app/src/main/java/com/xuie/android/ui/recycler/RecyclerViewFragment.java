package com.xuie.android.ui.recycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.android.R;
import com.xuie.android.ui.recycler.axis.AxisFragment;
import com.xuie.android.ui.recycler.diffutil.DiffUtilFragment;
import com.xuie.android.ui.recycler.discrete.DiscreteFragment;
import com.xuie.android.ui.recycler.normal.NormalFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class RecyclerViewFragment extends Fragment {

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

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
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
