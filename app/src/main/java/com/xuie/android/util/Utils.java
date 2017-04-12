package com.xuie.android.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.xuie.android.App;
import com.xuie.android.provider.ColorContract;

import java.util.Random;

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

    public static void initColorProvider() {
        if (PreferenceUtils.getBoolean("run_data", false)) {
            return;
        }

        int[] colors = {
                ContextCompat.getColor(App.getContext(), android.R.color.holo_blue_dark),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_blue_bright),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_blue_light),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_orange_dark),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_purple),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_red_dark),
                ContextCompat.getColor(App.getContext(), android.R.color.holo_red_light),
        };

        int per = -1, cur;
        Random r = new Random();
        for (int i = 0; i < 60; i++) {
            do {
                cur = r.nextInt(8);
            } while (cur == per);
            per = cur;
//            textPictures.add(new TextColor("This is element #" + i, colors[cur]));
            ContentValues contentValues = new ContentValues();
            contentValues.put(ColorContract.ColorEntry.COLUMN_NAME, "element #" + cur);
            contentValues.put(ColorContract.ColorEntry.COLUMN_COLOR, colors[cur]);
            App.getContext().getContentResolver().insert(ColorContract.ColorEntry.CONTENT_URI, contentValues);
        }

        PreferenceUtils.setBoolean("run_data", true);
    }
}
