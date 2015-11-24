package com.xuie.androiddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xuie.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuie on 15-10-30.
 */
public class DataSheetView extends View {

    private Context context;
    private boolean isOpenClick;
    // 画笔
    private Paint mPaint;
    private int mHeight;
    // 画笔大小
    float lineWidth = 10.0f;
    float textSize = 30.0f;
    // 底部文件的间隙
    float textSpace = 10.0f;
    // 视图宽、高
    private int mWidth;
    // 触摸时显示的内容
    private String text = "Running surface";
    // 左上角坐标和右下角坐标
    private float xStart = 10.0f + 3 * lineWidth;
    private float yStart = 10.0f + 6 * lineWidth;
    private float xEnd;
    private float yEnd;
    // 图片
    private Bitmap icon;
    private Matrix matrix;
    private Path mPath;
    // 模拟坐标容器
    private List<PointBean> simulationDatas = new ArrayList<>();

    public DataSheetView(Context context) {
        this(context, null);
    }

    public DataSheetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataSheetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomDataSheetView, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomDataSheetView_clickable:
                    isOpenClick = a.getBoolean(attr, false);
                    break;
            }
        }
        a.recycle();
//        Log.d("xuie", "isOpenClick:" + isOpenClick);

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
        mPaint.setStyle(Paint.Style.FILL); // 设置实心/空心
        mPaint.setDither(true);
        matrix = new Matrix();
        mPath = new Path();

        simulationDatas.add(new PointBean(0, 0.5f));
        for (int i = 0; i < 5; i++) {
            float r = (float) Math.random();
            PointBean bean = new PointBean();
            bean.x = simulationDatas.get(i).x + r;
            bean.y = r;
            simulationDatas.add(bean);
        }
        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_data_sheet_top);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        // 定位好左上和右下角的位置
        xEnd = mWidth - xStart;
        yEnd = mHeight - yStart;
//        Log.d("xuie", "width:" + mWidth + ", height:" + mHeight + ", xEnd:" + xEnd);

        // 画中间水平细线
        mPaint.setStrokeWidth(lineWidth / 2);
        mPaint.setColor(Color.parseColor("#44474c"));
        canvas.drawLine(xStart, yEnd / 5, xEnd, yEnd / 5, mPaint);
        canvas.drawLine(xStart, yEnd / 5 * 2, xEnd, yEnd / 5 * 2, mPaint);
        canvas.drawLine(xStart, yEnd / 5 * 3, xEnd, yEnd / 5 * 3, mPaint);
        canvas.drawLine(xStart, yEnd / 5 * 4, xEnd, yEnd / 5 * 4, mPaint);

        // 画两条黑线框架
        mPaint.setStrokeWidth(lineWidth / 2);
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(xStart, yStart, xStart, yEnd, mPaint);
        canvas.drawLine(xStart, yEnd, xEnd, yEnd, mPaint);
        canvas.drawCircle(xStart, yStart, lineWidth * 3 / 4, mPaint);
        canvas.drawCircle(xStart, yEnd, lineWidth * 3 / 4, mPaint);
        canvas.drawCircle(xEnd, yEnd, lineWidth * 3 / 4, mPaint);


        // 画点点相连的线和圆
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(Color.parseColor("#00b05f"));
        int length = simulationDatas.size();
        float countX = simulationDatas.get(length - 1).x;
        if (length > 1) {
            float min = 0.5f;
            int c = 0;

            for (int i = 1; i < length; i++) {
//                Log.d("xuie", "x:" + simulationDatas.get(i).x + ", y:" + simulationDatas.get(i).y);
                float x0 = simulationDatas.get(i - 1).x * ((mWidth - 2 * xStart) / countX) + xStart;
                float y0 = simulationDatas.get(i - 1).y * (mHeight - 2 * yStart) + yStart;
                float x1 = simulationDatas.get(i).x * ((mWidth - 2 * xStart) / countX) + xStart;
                float y1 = simulationDatas.get(i).y * (mHeight - 2 * yStart) + yStart;

                // 算出最高的那个点
                if (simulationDatas.get(i).y < min) {
                    min = simulationDatas.get(i).y;
                    c = i;
                }

//                setLayerType(LAYER_TYPE_SOFTWARE, null);
                canvas.drawLine(x0, y0, x1, y1, mPaint);
                canvas.drawCircle(x0, y0, lineWidth, mPaint);
                canvas.drawCircle(x1, y1, lineWidth, mPaint);
            }
            // 绘制最高的点的圆和增加图片
            float x2 = simulationDatas.get(c).x * ((mWidth - 2 * xStart) / countX) + xStart;
            float y2 = simulationDatas.get(c).y * (mHeight - 2 * yStart) + yStart;
            float scale = lineWidth * 4 / icon.getHeight();
            matrix.setScale(scale, scale);
            matrix.postTranslate(x2 - lineWidth * 3 - 5, y2 - lineWidth * 6 - 5);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.parseColor("#b1abad"));
            canvas.drawCircle(x2, y2, lineWidth * 2, mPaint);
            canvas.drawBitmap(icon, matrix, mPaint);
        }
        // 画底部字样
//        canvas.drawText(text, );

        // 画触摸显示值
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
//        mPaint.setColor(Color.parseColor("#93622a"));
        if (xTouch > 0 && yTouch > 0) {
            canvas.drawText(text, xTouch, yTouch, mPaint);
        }

        // 画底部范围阴影
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(20); // http://da-en.iteye.com/blog/669661
        mPath.moveTo(xStart, yEnd);
        for (int i = 0; i < length; i++) {
            float x3 = simulationDatas.get(i).x * ((mWidth - 2 * xStart) / countX) + xStart;
            float y3 = simulationDatas.get(i).y * (mHeight - 2 * yStart) + yStart;
            mPath.lineTo(x3, y3);
        }
        mPath.lineTo(xEnd, yEnd);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private float xTouch = 0.0f, yTouch = 0.0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isOpenClick) {
            return super.onTouchEvent(event);
        }

        xTouch = event.getX();
        yTouch = event.getY();
//        Log.d("xuie", "x:" + xTouch + ", y:" + yTouch);
        if (xTouch < xStart
                || xTouch > xEnd
                || yTouch < yStart
                || yTouch > yEnd
                ) {
            xTouch = yTouch = 0.0f;
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    xTouch = yTouch = 0.0f;
                    break;
            }
        }

        invalidate();
        return true;
    }
}
