package com.xuie.androiddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.xuie.androiddemo.R;

public class RoundProgressBar extends View {
    /**
     * 画笔对象的引用
     */
    Paint paint;

    RectF rectF;

    SweepGradient gradient;

    Matrix matrix;

    /**
     * 圆环的颜色
     */
    int roundColor;

    /**
     * 圆环进度的颜色
     */
    int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    int textColor;

    /**
     * 圆环的宽度
     */
    float roundWidth;

    /**
     * 最大进度
     */
    int max = 100;

    /**
     * 当前进度，默认50%
     */
    float progress = 50.0f;
    /**
     * 进度的风格，实心或者空心
     */
    int style;

    final int STROKE = 0;
    final int FILL = 1;

    String text_progress = "10%";
    String text_times = "10";

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        roundColor = ta.getColor(R.styleable.RoundProgressBar_roundColor,
                Color.parseColor("#373a49"));
        roundProgressColor = ta.getColor(R.styleable.RoundProgressBar_roundProgressColor,
                Color.parseColor("#4fd2c2"));
        textColor = ta.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        roundWidth = ta.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        style = ta.getInt(R.styleable.RoundProgressBar_style, 0);
        ta.recycle();

        paint = new Paint();
        rectF = new RectF();
        matrix = new Matrix();
        gradient = new SweepGradient(0f, 0f, Color.parseColor("#3a4352"),
                Color.parseColor("#51d1c2"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画最外层的大圆环
         */
        float centreX = getWidth() / 2;
        float centreY = getHeight() / 2;
        float radius = ((centreX < centreY) ? centreX : centreY) - roundWidth; // 圆环的半径
        paint.setColor(roundColor); // 设置圆环的颜色
        paint.setStyle(Style.STROKE); // 设置空心
        paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
        paint.setAntiAlias(true); // 消除锯齿
        // 画出圆环背景
        canvas.drawCircle(centreX, centreY, radius, paint);

        /**
         * 画圆弧 ，画圆环的进度
         */
        // 保存画布
        canvas.save();
        canvas.rotate(-90, centreX, centreY);

        // 设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        // 设置颜色激变
        matrix.setTranslate(centreX, centreY);
        gradient.setLocalMatrix(matrix);
//        SweepGradient gradient = new SweepGradient(centreX, centreY,
//                new int[]{Color.parseColor("#3a4352"), Color.parseColor("#51d1c2")},
//                new float[]{0, 1});

//        RadialGradient gradient = new RadialGradient(centreX, centreY, radius, Color.parseColor("#3a4352"),
//                Color.parseColor("#51d1c2"), Shader.TileMode.CLAMP);

//        LinearGradient gradient = new LinearGradient(centreX, 0, centreX,
//                centreY + radius, Color.parseColor("#3a4352"),
//                Color.parseColor("#51d1c2"), Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        // 用于定义的圆弧的形状和大小的界限
        rectF.set(centreX - radius, centreY - radius, centreX + radius, centreY + radius);

        switch (style) {
            case STROKE: {
                paint.setStyle(Style.STROKE);
                canvas.drawArc(rectF, 0, 360 * progress, false, paint); // 根据进度画圆弧
                break;
            }
            case FILL: {
                paint.setStyle(Style.FILL_AND_STROKE);
                if (progress != 0) {
                    canvas.drawArc(rectF, 0, 360 * progress, true, paint); // 根据进度画圆弧
                }
                break;
            }
        }
        canvas.restore();
        paint.setShader(null);

        /**
         * 画point
         */
        paint.setStyle(Style.FILL);
        paint.setColor(roundProgressColor);
        float dirs = (float) (radius / Math.sqrt(2) - roundWidth * 1.5);
        canvas.drawCircle(centreX - dirs, centreY + dirs, roundWidth / 2, paint);

        /**
         * 画圆内数据
         */
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextSize(roundWidth);
        // 画“COMPLETED”
        canvas.drawText("COMPLETED", centreX, centreY - paint.getTextSize() * 6, paint);

        // 画“132”
        paint.setTextSize(roundWidth * 4);
        canvas.drawText(text_times, centreX, centreY, paint);

        // 画“50%”
        paint.setTextSize(roundWidth * 2);
        paint.setColor(Color.parseColor("#888b90"));
        canvas.drawText(text_progress, centreX, centreY + paint.getTextSize() * 2, paint);
    }

    /**
     * 初始化进度的最大值
     *
     * @param maxTimes
     */
    public synchronized void setMaxTimes(int maxTimes) {
        if (maxTimes < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = maxTimes;
    }

    public synchronized int getMaxTimes() {
        return max;
    }

    /**
     * 更新进度
     *
     * @param times
     */
    public synchronized void setTimes(int times) {
        this.text_times = times + "";
        this.progress = times * 1.0f / max;
        this.text_progress = (int) (progress * 100) + "%";
//        Log.d("xuie", "text_times:" + text_times + ", progress:" + progress + ", text_progress:" + text_progress);
        postInvalidate();
    }

    /**
     * 获取当前进度百分比
     *
     * @return
     */
    public float getProgress() {
        return progress;
    }

}