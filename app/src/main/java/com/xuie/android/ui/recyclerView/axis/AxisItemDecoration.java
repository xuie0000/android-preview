package com.xuie.android.ui.recyclerView.axis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xuie on 17-8-9.
 * http://www.jianshu.com/p/9a796bb23a47
 */

public class AxisItemDecoration extends RecyclerView.ItemDecoration {
    public static final int OUT_BOTTOM_HEIGHT = 2;
    private Paint mPaint;

    public AxisItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(6);
//        mPaint.setTextSize(12 * Resources.getSystem().getDisplayMetrics().density);
//        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int left = view.getLeft();
            int right = view.getRight();
            int bottom = view.getBottom();
            if (i == childCount - 1)
                break;
            c.drawRect(left + 30, bottom, right - 30, bottom + OUT_BOTTOM_HEIGHT, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        // 获取Item的总数
        int childCount = parent.getChildCount();
        // 遍历Item
        for (int i = 0; i < childCount; i++) {
            // 获取每个Item的位置
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);

            // 左右上下的位置
            int left = view.getLeft();
            int right = view.getRight();
            int top = view.getTop();
            int bottom = view.getBottom();

            // 记录中心位置 (宽1/5，高1/2位置）
            int x = left + (right - left) / 5;
            int y = top + (bottom - top) / 2;

            if (index == 0) {
                c.drawLine(x, y, x, bottom, mPaint);
                c.drawCircle(x, y, 16, mPaint);
            } else {
                c.drawLine(x, top, x, y, mPaint);
                c.drawLine(x, y, x, bottom, mPaint);
                c.drawCircle(x, y, 16, mPaint);
            }
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, OUT_BOTTOM_HEIGHT);// default (0, 0, 0, 0)
    }
}
