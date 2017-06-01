package com.xuie.android.provider;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;

import com.xuie.android.App;

import java.util.Random;

/**
 * Created by xuie on 2017/6/1 0001.
 */

public class ColorInitTask extends AsyncTask<Void, Integer, Boolean> {

    public ColorInitTask() {
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // clean db
        App.getContext().getContentResolver().delete(ColorContract.ColorEntry.CONTENT_URI, null, null);

        // add database
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
            ContentValues contentValues = new ContentValues();
            contentValues.put(ColorContract.ColorEntry.COLUMN_NAME, "element #" + cur);
            contentValues.put(ColorContract.ColorEntry.COLUMN_COLOR, colors[cur]);
            App.getContext().getContentResolver().insert(ColorContract.ColorEntry.CONTENT_URI, contentValues);
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
