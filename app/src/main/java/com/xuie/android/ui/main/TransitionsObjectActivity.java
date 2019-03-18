package com.xuie.android.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xuie.android.R;
import com.xuie.android.util.BlurBitmap;

/**
 * @author xuie
 */
public class TransitionsObjectActivity extends AppCompatActivity {

    private ImageView bluredImg;
    private ImageView originImg;
    private SeekBar seekBar;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions_object);
        bluredImg = findViewById(R.id.blured_img);
        originImg = findViewById(R.id.origin_img);
        seekBar = findViewById(R.id.seek_bar);
        tvProgress = findViewById(R.id.progress);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Bitmap tempBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image_big);
        Bitmap finalBitmap = BlurBitmap.blur(this, tempBitmap);

        bluredImg.setImageBitmap(finalBitmap);
        originImg.setImageBitmap(tempBitmap);

        setSeekBar();
    }

    /**
     * 处理seekBar滑动事件
     */
    private void setSeekBar() {
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                originImg.setImageAlpha((int) (255 - progress * 2.55));
                tvProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // startActivity(getParentActivityIntent());
    }
}
