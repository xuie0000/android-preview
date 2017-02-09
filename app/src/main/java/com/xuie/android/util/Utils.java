package com.xuie.android.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

/**
 * Created by xuie on 17-2-9.
 */

public class Utils {

    public static Intent getDefaultIntent(Activity activity) {
        // 截屏
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();

        // 将Bitmap转换为Uri
        String pathOfBmp = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bmp, "title", null);
        Uri bmpUri = Uri.parse(pathOfBmp);

        // 清理截屏缓存
        dView.setDrawingCacheEnabled(false);
        dView.destroyDrawingCache();

//        File file = BitmapUtils.Drawable2File(this, R.mipmap.ic_launcher, Environment.getExternalStorageDirectory() + "/test.png");
//        Uri bmpUri = BitmapUtils.File2Uri(file);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        return shareIntent;
    }
}
