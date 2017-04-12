package com.xuie.android.ui.diffutil;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * https://github.com/mrmike/DiffUtil-sample
 */
public class DiffUtilFragment extends Fragment {
    private ActorAdapter adapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diff_util, container, false);
        ButterKnife.bind(this, view);

        adapter = new ActorAdapter(ActorRepository.getActorListSortedByRating());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(Menu.NONE, R.id.sort_by_name, Menu.NONE, "Sort by name").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(Menu.NONE, R.id.sort_by_rating, Menu.NONE, "Sort by rating").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(Menu.NONE, R.id.sort_by_birth, Menu.NONE, "Sort by year of birth").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                adapter.swapItems(ActorRepository.getActorListSortedByName());
                break;
            case R.id.sort_by_rating:
                adapter.swapItems(ActorRepository.getActorListSortedByRating());
                break;
            case R.id.sort_by_birth:
                adapter.swapItems(ActorRepository.getActorListSortedByYearOfBirth());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
