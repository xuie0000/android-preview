package com.xuie.androiddemo.ui.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xuie.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationFragment extends Fragment {

    enum AniType {
        ANIMATION,
        OBJ_ANIMATION,
    }

    @BindView(R.id.animation) RadioButton animation;
    @BindView(R.id.animator) RadioButton animator;
    @BindView(R.id.alpha) TextView alpha;
    @BindView(R.id.scale) TextView scale;
    @BindView(R.id.translate) TextView translate;
    @BindView(R.id.rotate) TextView rotate;
    @BindView(R.id.association) TextView association;

    AniType aniType = AniType.ANIMATION;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animation.setOnClickListener(v -> aniType = AniType.ANIMATION);
        animator.setOnClickListener(v -> aniType = AniType.OBJ_ANIMATION);

        alpha.setOnClickListener(v -> {
            if (aniType == AniType.ANIMATION) {
                Animation alphaAnimation;
                alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//                alphaAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);

                alpha.startAnimation(alphaAnimation);
            } else {
                ObjectAnimator alphaAnimator;
                alphaAnimator = ObjectAnimator.ofFloat(alpha, "alpha", 1.0f, 0.0f, 1.0f);
                alphaAnimator.setDuration(1000);
//                alphaAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.alpha);
//                alphaAnimator.setTarget(alpha);

                alphaAnimator.start();
                // ViewPropertyAnimator
//                alpha.animate()
//                        .rotation(90f) // 旋转90度
//                        .scaleX(0.5f) // 缩放0.5倍
//                        .translationX(100f) // X轴移动100
//                        .alpha(0.5f) // 透明0.5
//                        .start();
            }
        });

        scale.setOnClickListener(v -> {
            if (aniType == AniType.ANIMATION) {
                Animation scaleAnimation;
                //默认组件原点为中心
                scaleAnimation = new ScaleAnimation(0.0f, 1, 0.0f, 1);
//                //指定为组件的中心
//                scaleAnimation = new ScaleAnimation(0.2f, 1, 0.2f, 1, scale.getWidth() / 2, scale.getHeight() / 2);
//                scaleAnimation.setDuration(1000);

//                scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
                scale.startAnimation(scaleAnimation);
            } else {
                ObjectAnimator scaleAnimator;
                scaleAnimator = ObjectAnimator.ofFloat(scale, "scaleX", 1.0f, 0.2f, 1.0f).setDuration(1000);
//                scaleAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.scale);
//                scaleAnimator.setTarget(scale);
                scaleAnimator.start();
            }
        });

        translate.setOnClickListener(v -> {
            if (aniType == AniType.ANIMATION) {
                Animation translateAnimation;
                translateAnimation = new TranslateAnimation(0, 0, 0, 200);
                translateAnimation.setDuration(500);
//                translateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.traslate);
                translate.startAnimation(translateAnimation);
            } else {
                ObjectAnimator translateAnimator;
                translateAnimator = ObjectAnimator.ofFloat(translate, "translationX", 0, 300, -200, 0);
                translateAnimator.setDuration(2000);
//                translateAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.translate);
//                translateAnimator.setTarget(translate);
                translateAnimator.start();
            }
        });


        rotate.setOnClickListener(v -> {
            if (aniType == AniType.ANIMATION) {
                Animation rotateAnimation;
//                //不指定中心，则默认组件原点为中心
//                rotateAnimation = new RotateAnimation(0, 360);
//                //指定中心。
//                rotateAnimation = new RotateAnimation(0, 360, rotate.getWidth() / 2, rotate.getHeight() / 2);
                //指定旋转中心的类型
                /**
                 * Animation.RELATIVE_TO_SELF 参照自身
                 * 0.5f 位置范围（0～1.0f），0.5f表示在中间
                 */
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(500);
                rotateAnimation.setInterpolator(new AccelerateInterpolator());
//                rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                rotate.startAnimation(rotateAnimation);
            } else {
//                ObjectAnimator rotateAnimator;
//                // "rotation" "rotationX" "rotationY"
//                rotateAnimator = ObjectAnimator.ofFloat(rotate, "rotation", 0, 360);
////                rotateAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate);
////                rotateAnimator.setTarget(rotate);
//                rotateAnimator.start();

                AnimatorSet rotateSet = new AnimatorSet();
                rotateSet.playTogether(ObjectAnimator.ofFloat(rotate, "rotation", 0, 15), //
                        ObjectAnimator.ofFloat(rotate, "pivotX", rotate.getHeight() / 2),//
                        ObjectAnimator.ofFloat(rotate, "pivotY", rotate.getHeight() / 2)//
                );
                rotateSet.setDuration(1000).start();
            }
        });

        association.setOnClickListener(v -> {
            if (aniType == AniType.ANIMATION) {
                AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.3f, 1f);
                alphaAnimation1.setDuration(2000);
                ScaleAnimation scaleAnimation1 = new ScaleAnimation(0, 1, 0, 1,
                        association.getWidth() / 2, association.getHeight() / 2);
                scaleAnimation1.setDuration(2000);
                RotateAnimation rotateAnimation1 = new RotateAnimation(0, 360,
                        association.getWidth() / 2, association.getHeight() / 2);
                rotateAnimation1.setDuration(2000);
                AnimationSet set = new AnimationSet(true);
                set.addAnimation(alphaAnimation1);
                set.addAnimation(scaleAnimation1);
                set.addAnimation(rotateAnimation1);
                set.setFillEnabled(true);
                set.setDuration(2000);
                set.setRepeatCount(0);
                association.startAnimation(set);
//                Animation associationAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.all);
//                association.startAnimation(associationAnimation);
            } else {
                PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("translationX", 0, 200, -200, 0);
                PropertyValuesHolder ty = PropertyValuesHolder.ofFloat("translationY", 0, 100, -100, 0);
                PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.3f, 1.0f);
                ObjectAnimator.ofPropertyValuesHolder(association, tx, ty, sx).setDuration(2000).start();
//                Animator associationAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.association);
//                associationAnimator.setTarget(association);
//                associationAnimator.start();
            }
        });
    }

    private void showCode(View view, String string) {
        Snackbar.make(view, ">>" + string, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}
