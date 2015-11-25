package com.xuie.androiddemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xuie.androiddemo.R;

public class CircularProgress extends ImageView {
    CircularProgressDrawable circularProgressDrawable;
    int progress;
    int colorProgress;
    int paddingProgress;

    public CircularProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    public CircularProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.CircularProgress);
        if (attr == null) {
            return;
        }

        progress = attr.getInt(R.styleable.CircularProgress_cp_defaultProgress, 0);
        colorProgress = attr.getColor(R.styleable.CircularProgress_cp_colorProgress, Color.BLUE);
        paddingProgress = attr.getDimensionPixelSize(R.styleable.CircularProgress_cp_paddingProgress, 0);
        attr.recycle();
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            drawable.draw(canvas);
        } else {
            super.onDraw(canvas);
        }
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        if (circularProgressDrawable == null) {
            int sweepAngle = (this.getWidth() - this.getHeight()) / 2;
            int size = this.getHeight() - paddingProgress * 2;
            circularProgressDrawable = new CircularProgressDrawable(size, (int) dpToPx(4), colorProgress);
            int left = sweepAngle + paddingProgress;
            circularProgressDrawable.setBounds(left, paddingProgress, left, paddingProgress);
        }

        float sweepAngle1 = 360.0F / (float) 100 * (float) progress;
        circularProgressDrawable.setSweepAngle(sweepAngle1);
        circularProgressDrawable.draw(canvas);
    }

    public void start() {
        running(1, 100, 1500);
    }

    public void start(int fromProgress, int toProgress, long duration) {
        running(fromProgress, toProgress, duration);
    }

    private void running(int fromProgress, int toProgress, long duration) {
        ValueAnimator animation = ValueAnimator.ofInt(fromProgress, toProgress);
        animation.setDuration(duration);
//        animation.setRepeatMode(ValueAnimator.REVERSE);
//        animation.setRepeatCount(4);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        animation.start();
    }

    private float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    class CircularProgressDrawable extends Drawable {
        private float mSweepAngle;
        private float mStartAngle;
        private int mSize;
        private int mStrokeWidth;
        private int mStrokeColor;
        private RectF mRectF;
        private Paint mPaint;
        private Path mPath;

        public CircularProgressDrawable(int size, int strokeWidth, int strokeColor) {
            this.mSize = size;
            this.mStrokeWidth = strokeWidth;
            this.mStrokeColor = strokeColor;
            this.mStartAngle = -90.0F;
            this.mSweepAngle = 0.0F;
        }

        public void setSweepAngle(float sweepAngle) {
            this.mSweepAngle = sweepAngle;
        }

        public int getSize() {
            return this.mSize;
        }

        public void draw(Canvas canvas) {
            Rect bounds = this.getBounds();
            if (this.mPath == null) {
                this.mPath = new Path();
            }

            this.mPath.reset();
            this.mPath.addArc(this.getRect(), this.mStartAngle, this.mSweepAngle);
            this.mPath.offset((float) bounds.left, (float) bounds.top);
            canvas.drawPath(this.mPath, this.createPaint());
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }

        public int getOpacity() {
            return 1;
        }

        private RectF getRect() {
            if (this.mRectF == null) {
                int index = this.mStrokeWidth / 2;
                this.mRectF = new RectF((float) index, (float) index, (float) (this.getSize() - index), (float) (this.getSize() - index));
            }

            return this.mRectF;
        }

        private Paint createPaint() {
            if (this.mPaint == null) {
                this.mPaint = new Paint();
                this.mPaint.setAntiAlias(true);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
                this.mPaint.setColor(this.mStrokeColor);
            }

            return this.mPaint;
        }
    }

}