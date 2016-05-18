package com.xuie.androiddemo.view.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.view.activity.Show2Activity;
import com.xuie.androiddemo.view.activity.ShowActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransitionsFragment extends Fragment {
    @BindView(R.id.make_scene_transition_animation) Button makeSceneTransitionAnimation;
    @BindView(R.id.fab_button) Button fabButton;
    @BindView(R.id.over_shoot) ImageView overShoot;
    @BindView(R.id.cir_reveal_dst) ImageView cirRevealDst;
    @BindView(R.id.cir_reveal_hypot) ImageView cirRevealHypot;
    @BindView(R.id.cir_reveal_normal) ImageView cirRevealNormal;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transitions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.make_scene_transition_animation, R.id.fab_button,
            R.id.explode, R.id.slide, R.id.fade, R.id.over_shoot,
            R.id.cir_reveal_btn, R.id.cir_reveal_normal, R.id.cir_reveal_hypot})
    void onClick(View view) {
        Intent intent;
        ActivityOptions options;
        switch (view.getId()) {
            case R.id.make_scene_transition_animation:
                Logger.d("make_scene_transition_animation");
                intent = new Intent(getActivity(), ShowActivity.class);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), makeSceneTransitionAnimation, "share01");

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
            case R.id.fab_button:
                Logger.d("fab_button");
                intent = new Intent(getActivity(), ShowActivity.class);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        // 创建多个共享元素
                        Pair.create((View) makeSceneTransitionAnimation, "share01"),
                        Pair.create((View) fabButton, "share02")
                );

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
            case R.id.explode:
                Logger.d("explode");
                makeSceneTransitionAnimationNoParameter(0);
                break;
            case R.id.slide:
                Logger.d("slide");
                makeSceneTransitionAnimationNoParameter(1);
                break;
            case R.id.fade:
                Logger.d("fade");
                makeSceneTransitionAnimationNoParameter(2);
                break;
            case R.id.over_shoot:
                Logger.d("over_shoot");
                intent = new Intent(getActivity(), Show2Activity.class);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        overShoot, "shareOverShoot");
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
            case R.id.cir_reveal_btn:
                // http://blog.jobbole.com/77015/ 圆形显示
                if (cirRevealDst.getVisibility() != View.VISIBLE) {
                    Animator anim = ViewAnimationUtils.createCircularReveal(
                            cirRevealDst,
                            cirRevealDst.getWidth() / 2,
                            cirRevealDst.getHeight() / 2,
                            0,
                            Math.max(cirRevealDst.getWidth(), cirRevealDst.getHeight()));

//                    anim.setDuration(1000);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            cirRevealDst.setVisibility(View.VISIBLE);
                        }
                    });

                    anim.start();
                } else {
                    Animator anim = ViewAnimationUtils.createCircularReveal(
                            cirRevealDst,
                            cirRevealDst.getWidth() / 2,
                            cirRevealDst.getHeight() / 2,
                            Math.max(cirRevealDst.getWidth(), cirRevealDst.getHeight()),
                            0);

//                    anim.setDuration(1000);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            cirRevealDst.setVisibility(View.INVISIBLE);
                        }
                    });

                    anim.start();
                }
                break;
            case R.id.cir_reveal_normal:
                Animator mNormalAnimator = ViewAnimationUtils.createCircularReveal(
                        cirRevealNormal,
                        cirRevealNormal.getWidth() / 2,
                        cirRevealNormal.getHeight() / 2,
                        0,
                        Math.max(cirRevealNormal.getWidth(), cirRevealNormal.getHeight()));
//                mNormalAnimator.setDuration(2000);
                mNormalAnimator.setInterpolator(new AccelerateInterpolator());
                mNormalAnimator.start();
                break;
            case R.id.cir_reveal_hypot:
                float endRadius = (float) Math.hypot(cirRevealHypot.getWidth(),
                        cirRevealHypot.getHeight());//勾股定理得到斜边长度
                Animator mHypotAnimator = ViewAnimationUtils.createCircularReveal(cirRevealHypot,
                        cirRevealHypot.getWidth(), 0, 0, endRadius);
                mHypotAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mHypotAnimator.start();
                break;
        }
    }

    private void makeSceneTransitionAnimationNoParameter(int flag) {
        Intent intent = new Intent(getActivity(), ShowActivity.class);
        intent.putExtra("flag", flag);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

}
