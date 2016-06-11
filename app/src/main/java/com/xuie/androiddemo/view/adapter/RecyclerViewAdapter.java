package com.xuie.androiddemo.view.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.TextColor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<TextColor> textPictures;

    public RecyclerViewAdapter(List<TextColor> textPictures) {
        this.textPictures = textPictures;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextColor textPicture = textPictures.get(position);
        RecyclerViewHolder vh = ((RecyclerViewHolder) holder);
        ((CardView)holder.itemView).setCardBackgroundColor(textPicture.getColor());
        vh.title.setText(textPicture.getText());

        Animator[] anims = new Animator[]{
                ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1, 1.1f, 1)
        };

        for (Animator anim : anims) {
            anim.setDuration(300).start();
        }
    }

    @Override public int getItemCount() {
        return textPictures.size();
    }

    protected class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;
        public RecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            title.setOnClickListener(v -> Logger.d("Element " + getAdapterPosition() + " clicked."));
        }
    }

}
