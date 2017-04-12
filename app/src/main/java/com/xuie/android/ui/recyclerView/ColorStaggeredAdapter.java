package com.xuie.android.ui.recyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuie.android.R;
import com.xuie.cursoradapter.RecyclerViewCursorViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuie on 2017/4/12 0012.
 */

public class ColorStaggeredAdapter extends ColorAdapter {
    private static final String TAG = "ColorStaggeredAdapter";
    private static final int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;

    /**
     * Constructor.
     *
     * @param context The Context the Adapter is displayed in.
     */
    public ColorStaggeredAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public View onCreateView(Context context, Cursor cursor, ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateView viewType:" + viewType);
        if (viewType == ITEM_TYPE_LARGER) {
            return LayoutInflater.from(context).inflate(R.layout.item_text_row_larger, parent, false);
        }
        return super.onCreateView(context, cursor, parent, viewType);
    }

    @Override
    public RecyclerViewCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder viewType:" + viewType);
        if (viewType == ITEM_TYPE_LARGER) {
            return new ColorStaggeredViewHolder(getCursorView(parent, viewType));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewCursorViewHolder holder, int position) {
        Log.d(TAG, "position:" + position);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
    }

    class ColorStaggeredViewHolder extends RecyclerViewCursorViewHolder {
        CardView card;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.subText) TextView subText;

        ColorStaggeredViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            card = (CardView) itemView;
        }

        @Override
        public void bindCursor(Cursor cursor) {
            title.setText(cursor.getString(NAME_INDEX));
            subText.setText(cursor.getString(NAME_INDEX));
            card.setCardBackgroundColor(cursor.getInt(COLOR_INDEX));
            startAnimation(itemView);
        }
    }

}
