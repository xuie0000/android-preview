package com.xuie.android.ui.recyclerView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuie.android.R;
import com.xuie.android.provider.ColorContract;
import com.xuie.cursoradapter.RecyclerViewCursorAdapter;
import com.xuie.cursoradapter.RecyclerViewCursorViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuie on 2017/4/12 0012.
 */

public class ColorAdapter extends RecyclerViewCursorAdapter<ColorAdapter.ColorViewHolder> {

    /**
     * Column projection for the query to pull Colors from the database.
     */
    public static final String[] MOVIE_COLUMNS = new String[]{
            ColorContract.ColorEntry._ID,
            ColorContract.ColorEntry.COLUMN_NAME,
            ColorContract.ColorEntry.COLUMN_COLOR
    };

    private static final int NAME_INDEX = 1;
    private static final int COLOR_INDEX = 2;

    /**
     * Constructor.
     *
     * @param context The Context the Adapter is displayed in.
     */
    public ColorAdapter(Context context) {
        super(context);
        setupCursorAdapter(null, 0, R.layout.item_text_row, false);
    }

    /**
     * Returns the ViewHolder to use for this adapter.
     */
    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColorViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    /**
     * Moves the Cursor of the CursorAdapter to the appropriate position and binds the view for
     * that item.
     */
    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        // Move cursor to this position
        mCursorAdapter.getCursor().moveToPosition(position);

        // Set the ViewHolder
        setViewHolder(holder);

        // Bind this view
        mCursorAdapter.bindView(null, mContext, mCursorAdapter.getCursor());
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
