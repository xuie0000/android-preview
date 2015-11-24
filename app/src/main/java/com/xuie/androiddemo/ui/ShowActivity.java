package com.xuie.androiddemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.animation.OvershootInterpolator;

import com.xuie.androiddemo.R;

import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Intent intent = getIntent();
        if (intent != null) {
            int flag = intent.getIntExtra("flag", -1);
            // 设置不同的动画效果
            switch (flag) {
                case 0:
                    getWindow().setEnterTransition(new Explode());
                    break;
                case 1:
                    Slide slide = new Slide();
                    slide.setInterpolator(new OvershootInterpolator());
//                    slide.setDuration(2000l);
                    getWindow().setEnterTransition(slide);
                    break;
                case 2:
                    getWindow().setEnterTransition(new Fade());
                    getWindow().setExitTransition(new Fade());
                    break;
                case 3:
                    getWindow().setEnterTransition(enterTransition());
                    break;
            }
        }
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
    }

    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new OvershootInterpolator());
        bounds.setDuration(2000);
        return bounds;
    }

}
