package com.xuie.android.ui.recyclerView;

import android.content.Context;
import android.database.Cursor;
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
 * Created by xuie on 2017/4/12 0012.
 */

public class ColorStaggeredAdapter extends ColorAdapter {
    private static final String TAG = "ColorStaggeredAdapter";
    private static final int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;

    public ColorStaggeredAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_row_larger, parent, false);
        return new ColorStaggeredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
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
