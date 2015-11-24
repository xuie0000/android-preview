package com.xuie.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xuie on 15-11-9.
 */
public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        Log.d("xuie", "change:" + changed + ", l:" + l + ", t:" + t + ", r:" + r + ", b:" + b);
//        Log.d("xuie", "onLayout width:" + (r - l) + ", height:" + (b - t));
//
//        int cCount = getChildCount();
//        for (int i = 0; i < cCount; i++) {
//            View child = getChildAt(i);
//            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
//            int cl = child.getLeft();
//            int cr = child.getRight();
//            int ct = child.getTop();
//            int cb = child.getBottom();
//            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//            Log.d("xuie", "childWidth:" + childWidth + ", childHeight:" + childHeight);
//            Log.d("xuie", "cl:" + cl + ", cr: " + cr + ", ct: " + ct + ", cb: " + cb);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int width = MeasureSpec.getSize(widthMeasureSpec);
//        final int height = MeasureSpec.getSize(heightMeasureSpec);
//        Log.d("xuie", "onMeasure width:" + width + ", height:" + height);
    }

    /**
     * 如果在这里写动画，会影响外面onClick事件，如果
     * 不return true;就没有相关ACTION_DOWN和ACTION_UP事件数据
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                Log.d("xuie", "onTouchEvent ACTION_DOWN");
                ((View) getChildAt(0).getParent()).animate()
                        .scaleX(0.90f)
                        .scaleY(0.90f)
//                        .translationY(v.getHeight() / 4) // Translates by 1/4 of the view's height to compensate the scale
//                        .setDuration(750)
                        .start();
                return true;
//            case MotionEvent.ACTION_MOVE:
//                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                Log.d("xuie", "onTouchEvent ACTION_UP");
                ((View) getChildAt(0).getParent()).animate()
                        .scaleX(1f)
                        .scaleY(1f)
//                        .translationY(v.getHeight() / 4) // Translates by 1/4 of the view's height to compensate the scale
//                        .setDuration(750)
                        .start();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
