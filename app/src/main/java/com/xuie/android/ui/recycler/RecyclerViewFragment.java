package com.xuie.android.ui.recycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
    private View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter listAdapter = new ListAdapter(android.R.layout.simple_list_item_1);
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                default:
                    Navigation.findNavController(rootView).navigate(R.id.action_to_normal);
                    break;
                case 1:
                    Navigation.findNavController(rootView).navigate(R.id.action_to_diff_util);
                    break;
                case 2:
                    Navigation.findNavController(rootView).navigate(R.id.action_to_axis);
                    break;
                case 3:
                    Navigation.findNavController(rootView).navigate(R.id.action_to_discrete);
                    break;
            }
        });
        recyclerView.setAdapter(listAdapter);

        return rootView;
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
