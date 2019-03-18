package com.xuie.android.ui.adapter;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author xuie
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration() {
        margin = (int) (8 * Resources.getSystem().getDisplayMetrics().density);
    }

    public MarginDecoration(int dp) {
        margin = (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}