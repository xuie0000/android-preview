package com.xuie.android.ui.recycler.normal;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuie.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author xuie
 * @date 2017/4/12 0012
 */

public class ColorStaggeredAdapter extends ColorAdapter {
    private static final String TAG = "ColorStaggeredAdapter";
    private static final int ITEM_TYPE_NORMAL = 0, ITEM_TYPE_LARGER = 1;

    public ColorStaggeredAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_row_larger, parent, false);
        switch (viewType) {
            case ITEM_TYPE_NORMAL:
                return super.onCreateViewHolder(parent, viewType);
            case ITEM_TYPE_LARGER:
            default:
                return new ColorStaggeredViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        int vt = getItemViewType(viewHolder.getAdapterPosition());
        switch (vt) {
            default:
            case ITEM_TYPE_NORMAL:
                super.onBindViewHolder(viewHolder, cursor);
                return;
        }
        ColorStaggeredViewHolder vh = (ColorStaggeredViewHolder) viewHolder;

        vh.title.setText(cursor.getString(NAME_INDEX));
        vh.subText.setText(cursor.getString(NAME_INDEX));
        vh.card.setCardBackgroundColor(cursor.getInt(COLOR_INDEX));
        startAnimation(vh.itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
    }

    class ColorStaggeredViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.subText) TextView subText;

        ColorStaggeredViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            card = (CardView) itemView;
        }
    }
}
