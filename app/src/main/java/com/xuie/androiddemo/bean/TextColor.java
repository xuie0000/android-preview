package com.xuie.androiddemo.bean;

/**
 * Created by xuie on 15-12-13.
 */
public class TextColor {
    private String text;
    private int color;

    public TextColor(String text, int picture) {
        this.text = text;
        this.color = picture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
