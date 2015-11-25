package com.xuie.androiddemo.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.model.IModel.ImageModel;

public class ImageModelImpl implements ImageModel {

    private static ImageModelImpl instance;

    private ImageModelImpl() {

    }

    public static ImageModelImpl getInstance() {

        if (instance == null) {
            synchronized (ImageModelImpl.class) {
                if (instance == null)
                    instance = new ImageModelImpl();
            }
        }
        return instance;
    }

    @Override public void loadImage(String url, ImageView imageView) {
        Glide.with(App.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.img_on_laoding)
                .error(R.drawable.img_on_laoding)
                .crossFade()
                .into(imageView);
    }

    @Override public void loadImageWithTargetView(String url, SimpleTarget simpleTarget) {
        Glide.with(App.getContext())
                .load(url)
                .asBitmap()
                /*.placeholder(R.drawable.img_on_laoding)
                .error(R.drawable.img_on_laoding)*/
                .into(simpleTarget);
    }

    @Override
    public void loadImageWithListener(String url, SimpleTarget simpleTarget, RequestListener listener) {
        Glide.with(App.getContext())
                .load(url)
                .asBitmap()
                .listener(listener)
              /*  .placeholder(R.drawable.img_on_laoding)
                .error(R.drawable.img_on_laoding)*/
                .into(simpleTarget);
    }
}
