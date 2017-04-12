package com.xuie.android.ui.recyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
    private int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;
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
    public View onCreateView(Context context, Cursor cursor, ViewGroup parent, boolean attachToRoot) {
        return super.onCreateView(context, cursor, parent, attachToRoot);
    }

    @Override
    public RecyclerViewCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {

        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewCursorViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    class ColorViewHolder extends RecyclerViewCursorViewHolder {
        CardView card;
        @BindView(R.id.title) TextView title;

        ColorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            card = (CardView) itemView;
        }

        @Override
        public void bindCursor(Cursor cursor) {
            title.setText(cursor.getString(NAME_INDEX));
            card.setCardBackgroundColor(cursor.getInt(COLOR_INDEX));
            startAnimation(itemView);
        }
    }

}
