package com.xuie.android.ui.recyclerView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuie.android.R;
import com.xuie.android.provider.ColorContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuie on 2017/4/12 0012.
 */

public class ColorAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ColorAdapter";
    /**
     * Column projection for the query to pull Colors from the database.
     */
    public static final String[] MOVIE_COLUMNS = new String[]{
            ColorContract.ColorEntry._ID,
            ColorContract.ColorEntry.COLUMN_NAME,
            ColorContract.ColorEntry.COLUMN_COLOR
    };

    static final int NAME_INDEX = 1;
    static final int COLOR_INDEX = 2;

    public ColorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_row, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
//        cursor.moveToPosition(viewHolder.getAdapterPosition());
        ColorViewHolder vh = (ColorViewHolder) viewHolder;

        vh.title.setText(cursor.getString(NAME_INDEX));
        vh.card.setCardBackgroundColor(cursor.getInt(COLOR_INDEX));
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

    class ColorViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        @BindView(R.id.title) TextView title;

        ColorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            card = (CardView) itemView;
        }
    }
}
