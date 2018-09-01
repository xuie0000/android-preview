package com.xuie.android.ui.adapter;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xuie
 */
public class RecyclerStaggeredViewAdapter extends RecyclerViewAdapter {
    private static final int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;

    public RecyclerStaggeredViewAdapter(List<TextColor> textPictures) {
        super(textPictures);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return super.onCreateViewHolder(viewGroup, viewType);
        } else if (viewType == ITEM_TYPE_LARGER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text_row_larger, viewGroup, false);
            return new RecyclerStaggeredViewHolder(v);
        } else {
            throw new IllegalArgumentException("viewType is ERROR, ITEM_TYPE_LARGER, ITEM_TYPE_NORMAL");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            super.onBindViewHolder(holder, position);
        } else if (holder instanceof RecyclerStaggeredViewHolder) {
            TextColor textPicture = textPictures.get(position);

            RecyclerStaggeredViewHolder vh = ((RecyclerStaggeredViewHolder) holder);
            vh.card.setCardBackgroundColor(textPicture.getColor());
            vh.title.setText(textPicture.getText());
            vh.subText.setText(textPicture.getText());
            startAnimation(vh.itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
//        return position == getTopPosition() ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
    }

    class RecyclerStaggeredViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.subText) TextView subText;

        RecyclerStaggeredViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            card = (CardView) itemView;
            itemView.setOnClickListener(v -> Logger.d("Element " + getAdapterPosition() + " clicked."));
        }
    }

}
