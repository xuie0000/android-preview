package com.xuie.android.ui.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xuie.android.R;

import java.util.List;

/**
 * @author xuie
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TextColor> textPictures;

    public RecyclerViewAdapter(List<TextColor> textPictures) {
        this.textPictures = textPictures;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text_row, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextColor textPicture = textPictures.get(position);
        RecyclerViewHolder vh = ((RecyclerViewHolder) holder);
        vh.card.setCardBackgroundColor(textPicture.getColor());
        vh.title.setText(textPicture.getText());
        startAnimation(vh.itemView);
    }

    protected void startAnimation(View view) {
        Animator[] animators = new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
        };

        for (Animator anim : animators) {
            anim.setDuration(300).start();
        }
    }

    @Override
    public int getItemCount() {
        return textPictures.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView title;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            title = itemView.findViewById(R.id.title);
            title.setOnClickListener(v -> Logger.d("Element " + getAdapterPosition() + " clicked."));
        }
    }

}
