package com.xuie.androiddemo.model.IModel;

import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

public interface ImageModel {

    void loadImage(String url, ImageView imageView);

    void loadImageWithTargetView(String url, SimpleTarget simpleTarget);

    void loadImageWithListener(String url, SimpleTarget simpleTarget, RequestListener listener);
}
