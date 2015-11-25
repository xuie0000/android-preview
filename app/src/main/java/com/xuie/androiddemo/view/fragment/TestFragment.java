package com.xuie.androiddemo.view.fragment;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.widget.CircularProgress;
import com.xuie.androiddemo.widget.ClearableEditText;
import com.xuie.androiddemo.widget.ColorfulRingProgressView;
import com.xuie.androiddemo.widget.SectorProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {
    @Bind(R.id.status_text_view) TextView statusTextView;
    @Bind(R.id.shake_view) ImageView shakeView;

    @Bind(R.id.circle_progress_1) CircularProgress circleProgress1;
    @Bind(R.id.circle_progress_2) CircularProgress circleProgress2;
    @Bind(R.id.crpv) ColorfulRingProgressView crpv;
    @Bind(R.id.spv) SectorProgressView spv;
    @Bind(R.id.vector_drawable_cpu) ImageView vectorDrawableCpu;
    @Bind(R.id.vector_drawable_cpu_ani) ImageView vectorDrawableCpuAni;
    @Bind(R.id.email) ClearableEditText email;
    @Bind(R.id.email_text_input_layout) TextInputLayout emailTextInputLayout;
    @Bind(R.id.password) ClearableEditText password;
    @Bind(R.id.password_text_input_layout) TextInputLayout passwordTextInputLayout;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.circle_progress_1, R.id.circle_progress_2, R.id.crpv, R.id.spv, R.id.shake_view})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle_progress_1:
                circleProgress1.start();
                break;
            case R.id.circle_progress_2:
                circleProgress2.start(30, 90, 1000);
                break;
            case R.id.crpv:
                crpvAnimation();
                break;
            case R.id.spv:
                spvAnimation();
                break;
            case R.id.shake_view:
                shakeView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));
                break;
        }
    }

    @Override public void onResume() {
        super.onResume();
        Drawable drawable = vectorDrawableCpuAni.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        int mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
            statusTextView.setText(R.string.text_for_day_night_mode_night_no);
        } else if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
            statusTextView.setText(R.string.text_for_day_night_mode_night_yes);
        } else {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
            statusTextView.setText(R.string.text_for_day_night_mode_night_auto);
        }
    }

    private void crpvAnimation() {
//        ValueAnimator animator = ValueAnimator.ofFloat(0, crpv.getPercent());
//        animator.setDuration(1000);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float progress = (float) animation.getAnimatedValue();
//                crpv.setPercent(progress);
//            }
//        });
//        animator.start();
        ObjectAnimator animSpv = ObjectAnimator.ofFloat(crpv, "percent", 0, crpv.getPercent());
        animSpv.setInterpolator(new LinearInterpolator());
        animSpv.setDuration(1000);
        animSpv.start();
    }

    private void spvAnimation() {
        ObjectAnimator animSpv = ObjectAnimator.ofFloat(spv, "percent", 0, spv.getPercent());
        animSpv.setInterpolator(new LinearInterpolator());
        animSpv.setDuration(1000);
        animSpv.start();
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
