package com.xuie.androiddemo.view.fragment;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
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
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.xuie.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationFragment extends Fragment {
    enum MethodType {
        CODE,
        XML,
    }

    enum AniType {
        ANIMATION,
        OBJ_ANIMATION,
    }

    @BindView(R.id.alpha) TextView alpha;
    @BindView(R.id.scale) TextView scale;
    @BindView(R.id.translate) TextView translate;
    @BindView(R.id.rotate) TextView rotate;
    @BindView(R.id.association) TextView association;

    AniType aniType = AniType.ANIMATION;
    MethodType methodType = MethodType.CODE;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.alpha, R.id.scale, R.id.translate, R.id.rotate, R.id.association}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.alpha:
                if (aniType == AniType.ANIMATION) {
                    Animation alphaAnimation;
                    if (methodType == MethodType.CODE) {
                        alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
                        alphaAnimation.setDuration(1000);
                        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                    } else {
                        alphaAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                        // .xxx方法是以View默认位置来移动（参照最初位置），
                        // .xxxBy方法是以View当前的位置来移动（参照当前位置）
                        alpha.animate().translationXBy(50f);
                        return;
                    }
                    showCode(view, "alphaAnimation = new AlphaAnimation(0.3f, 1.0f);\nview.startAnimation(alphaAnimation)");
//                    alpha.startAnimation(alphaAnimation);
                    // ViewPropertyAnimator
                    alpha.animate()
                            .rotation(90f) // 旋转90度
                            .scaleX(0.5f) // 缩放0.5倍
                            .translationX(100f) // X轴移动100
                            .alpha(0.5f) // 透明0.5
                            .start();
                } else {
                    Animator alphaA;
                    if (methodType == MethodType.CODE) {
                        alphaA = ObjectAnimator.ofFloat(alpha, "alpha", 1.0f, 0.0f, 1.0f);
                        alphaA.setDuration(1000);
//                        alphaA.setInterpolator(new LinearInterpolator());
                    } else {
                        alphaA = AnimatorInflater.loadAnimator(getActivity(), R.animator.alpha);
                        alphaA.setTarget(alpha);
                    }
                    showCode(view, "ObjectAnimator.ofFloat(alpha, \"alpha\", 1.0f, 0.0f, 1.0f).start()");
                    alphaA.start();
                }

                break;
            case R.id.scale:
                if (aniType == AniType.ANIMATION) {
                    Animation scaleAnimation;
                    if (methodType == MethodType.CODE) {
                        //默认组件原点为中心
                        scaleAnimation = new ScaleAnimation(0.0f, 1, 0.0f, 1);
//                        //指定为组件的中心
//                        scaleAnimation = new ScaleAnimation(0.2f, 1, 0.2f, 1, scale.getWidth() / 2, scale.getHeight() / 2);
                        scaleAnimation.setDuration(1000);
                    } else {
                        scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
                    }
                    scale.startAnimation(scaleAnimation);
                    showCode(view, "scaleAnimation = new ScaleAnimation(0.0f, 1, 0.0f, 1);\nview.startAnimation(scaleAnimation)");
                } else {
                    Animator scaleA;
                    if (methodType == MethodType.CODE) {
                        scaleA = ObjectAnimator.ofFloat(scale, "scaleX", 1.0f, 0.2f, 1.0f).setDuration(1000);
                    } else {
                        scaleA = AnimatorInflater.loadAnimator(getActivity(), R.animator.scale);
                        scaleA.setTarget(scale);
                    }
                    scaleA.start();
                    showCode(view, "ObjectAnimator.ofFloat(scale, \"scaleX\", 1.0f, 0.2f, 1.0f).start()");
                }
                break;
            case R.id.translate:
                if (aniType == AniType.ANIMATION) {
                    Animation translateAnimation;
                    if (methodType == MethodType.CODE) {
                        translateAnimation = new TranslateAnimation(0, 0, 0, 200);
                        translateAnimation.setDuration(500);
                    } else {
                        translateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.traslate);
                    }
                    translate.startAnimation(translateAnimation);
                    showCode(view, "translateAnimation = new TranslateAnimation(0, 0, 0, 200);\nview.startAnimation(translateAnimation)");
                } else {
                    Animator translateA;
                    if (methodType == MethodType.CODE) {
                        translateA = ObjectAnimator.ofFloat(translate, "translationX", 0, 300, -200, 0);
                        translateA.setDuration(2000);
                    } else {
                        translateA = AnimatorInflater.loadAnimator(getActivity(), R.animator.translate);
                        translateA.setTarget(translate);
                    }
                    translateA.start();
                    showCode(view, "ObjectAnimator.ofFloat(translate, \"translationX\", 0, 300, -200, 0).start()");
                }
                break;
            case R.id.rotate:
                if (aniType == AniType.ANIMATION) {
                    Animation rotateAnimation;
                    if (methodType == MethodType.CODE) {
//                        //不指定中心，则默认组件原点为中心
//                        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
//                        //指定中心。
//                        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, rotate.getWidth() / 2, rotate.getHeight() / 2);
                        //指定旋转中心的类型
                        /**
                         * Animation.RELATIVE_TO_SELF 参照自身
                         * 0.5f 位置范围（0～1.0f），0.5f表示在中间
                         */
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setDuration(500);
                        rotateAnimation.setInterpolator(new AccelerateInterpolator());
                    } else {
                        rotateAnimation =
                                AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                    }
                    rotate.startAnimation(rotateAnimation);
                    showCode(view, "rotateAnimation = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)\n" +
                            "view.startAnimation(rotateAnimation)");
                } else {
                    Animator rotateA;
                    // "rotation" "rotationX" "rotationY"
                    if (methodType == MethodType.CODE) {
                        rotateA = ObjectAnimator.ofFloat(rotate, "rotation", 0, 360);
                    } else {
                        rotateA = AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate);
                        rotateA.setTarget(rotate);
                    }
                    rotateA.start();
                    showCode(view, "ObjectAnimator.ofFloat(rotate, \"rotation\", 0, 360).start()");
                }
                break;
            case R.id.association:
                if (aniType == AniType.ANIMATION) {
                    if (methodType == MethodType.CODE) {
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
                    } else {
                        Animation associationAnimation =
                                AnimationUtils.loadAnimation(getActivity(), R.anim.all);
                        association.startAnimation(associationAnimation);
                    }
                } else {
                    if (methodType == MethodType.CODE) {
                        PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("translationX", 0, 200, -200, 0);
                        PropertyValuesHolder ty = PropertyValuesHolder.ofFloat("translationY", 0, 100, -100, 0);
                        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.3f, 1.0f);
                        ObjectAnimator.ofPropertyValuesHolder(association, tx, ty, sx).setDuration(2000).start();
                    } else {
                        Animator associationA = AnimatorInflater.loadAnimator(getActivity(), R.animator.association);
                        associationA.setTarget(association);
                        associationA.start();
                    }
                    showCode(view, "ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder...)");
                }
                break;
        }
    }

    private void showCode(View view, String string) {
        Snackbar.make(view, ">>" + string, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @OnClick({R.id.code_rb, R.id.xml_rb, R.id.normal_rb, R.id.object_rb}) void Onclick(View view) {
        switch (view.getId()) {
            case R.id.code_rb:
                methodType = MethodType.CODE;
                break;
            case R.id.xml_rb:
                methodType = MethodType.XML;
                break;
            case R.id.normal_rb:
                aniType = AniType.ANIMATION;
                break;
            case R.id.object_rb:
                aniType = AniType.OBJ_ANIMATION;
                break;
        }
    }

}
