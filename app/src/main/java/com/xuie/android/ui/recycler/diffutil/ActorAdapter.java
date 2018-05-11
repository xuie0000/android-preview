package com.xuie.android.ui.recycler.diffutil;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuie.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuie
 */
public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {

    private List<Actor> actors = new ArrayList<>();

    public ActorAdapter(List<Actor> personList) {
        this.actors.addAll(personList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_actor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Actor actor = actors.get(position);
        holder.name.setText(actor.getName());
    }

    public void swapItems(List<Actor> actors) {
        final ActorDiffCallback diffCallback = new ActorDiffCallback(this.actors, actors);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.actors.clear();
        this.actors.addAll(actors);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.actor_name);
        }
    }
}
