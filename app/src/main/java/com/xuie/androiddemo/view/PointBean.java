package com.xuie.androiddemo.view;

/**
 * Created by xuie on 15-10-30.
 */
public class PointBean {
    float x;
    float y;

    public PointBean() {
    }

    public PointBean(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "PointBean{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
