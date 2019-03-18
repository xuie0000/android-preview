package com.xuie.android.ui.recycler.normal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xuie.android.R;

/**
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
        if (vt == ITEM_TYPE_NORMAL) {
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
        TextView title;
        TextView subText;

        ColorStaggeredViewHolder(View view) {
            super(view);
            card = (CardView) itemView;
            title = view.findViewById(R.id.title);
            subText = view.findViewById(R.id.subText);
        }
    }
}
