package com.xuie.androiddemo.ui.adapter;

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

/**
 * Created by xuie on 15-11-19.
 */
public class RecyclerStaggeredViewAdapter extends RecyclerViewAdapter {
    int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;

    public RecyclerStaggeredViewAdapter(List<TextColor> textPictures) {
        super(textPictures);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return super.onCreateViewHolder(viewGroup, viewType);
        } else if (viewType == ITEM_TYPE_LARGER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_larger_item, viewGroup, false);
            return new RecyclerStaggeredViewHolder(v);
        } else {
            throw new IllegalArgumentException("viewType is ERROR, ITEM_TYPE_LARGER, ITEM_TYPE_NORMAL");
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            super.onBindViewHolder(holder, position);
        } else if (holder instanceof RecyclerStaggeredViewHolder) {
            TextColor textPicture = textPictures.get(position);

            RecyclerStaggeredViewHolder vh = ((RecyclerStaggeredViewHolder) holder);
            ((CardView) holder.itemView).setCardBackgroundColor(textPicture.getColor());
            vh.title.setText(textPicture.getText());
            vh.subText.setText(textPicture.getText());

            Animator[] anims = new Animator[]{
                    ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1, 1.1f, 1),
                    ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1, 1.1f, 1)
            };

            for (Animator anim : anims) {
                anim.setDuration(300).start();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
//        return position == getTopPosition() ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
    }

    public class RecyclerStaggeredViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;
        @BindView(R.id.subText) TextView subText;

        public RecyclerStaggeredViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> Logger.d("Element " + getAdapterPosition() + " clicked."));
        }
    }

}
