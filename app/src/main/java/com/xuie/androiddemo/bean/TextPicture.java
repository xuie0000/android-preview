package com.xuie.androiddemo.bean;

/**
 * Created by xuie on 15-12-13.
 */
public class TextPicture {
    String text;
    int picture;

    public TextPicture(String text, int picture) {
        this.text = text;
        this.picture = picture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
