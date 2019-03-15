package com.xuie.android.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.xuie.android.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class TransitionsFragment extends Fragment {
    private Button makeSceneTransitionAnimation;
    private Button fabButton;
    private ImageView overShoot;
    private ImageView cirRevealDst;
    private ImageView cirRevealHypot;
    private ImageView cirRevealNormal;
    private Button explode;
    private Button slide;
    private Button fade;
    private Button cirRevealBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transitions, container, false);
        makeSceneTransitionAnimation = view.findViewById(R.id.make_scene_transition_animation);
        fabButton = view.findViewById(R.id.fab_button);
        overShoot = view.findViewById(R.id.over_shoot);
        cirRevealDst = view.findViewById(R.id.cir_reveal_dst);
        cirRevealHypot = view.findViewById(R.id.cir_reveal_hypot);
        cirRevealNormal = view.findViewById(R.id.cir_reveal_normal);
        explode = view.findViewById(R.id.explode);
        slide = view.findViewById(R.id.slide);
        fade = view.findViewById(R.id.fade);
        cirRevealBtn = view.findViewById(R.id.cir_reveal_btn);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        makeSceneTransitionAnimation.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OneActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), makeSceneTransitionAnimation, "share01");
            ActivityCompat.startActivity(Objects.requireNonNull(getActivity()), intent, options.toBundle());
        });

        fabButton.setOnClickListener(v -> {
            Logger.d("fab_button");
            Intent intent = new Intent(getActivity(), OneActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    // 创建多个共享元素
                    Pair.create(makeSceneTransitionAnimation, "share01"),
                    Pair.create(fabButton, "share02")
            );

            ActivityCompat.startActivity(Objects.requireNonNull(getActivity()), intent, options.toBundle());
        });

        explode.setOnClickListener(v -> makeSceneTransitionAnimationNoParameter(0));
        slide.setOnClickListener(v -> makeSceneTransitionAnimationNoParameter(1));
        fade.setOnClickListener(v -> makeSceneTransitionAnimationNoParameter(2));

        overShoot.setOnClickListener(v -> {
            Logger.d("over_shoot");
            Intent intent = new Intent(getActivity(), TransitionsObjectActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), overShoot, "shareOverShoot");
            ActivityCompat.startActivity(Objects.requireNonNull(getActivity()), intent, options.toBundle());
        });


        cirRevealBtn.setOnClickListener(v -> {
            // http://blog.jobbole.com/77015/ 圆形显示
            if (cirRevealDst.getVisibility() != View.VISIBLE) {
                Animator anim = ViewAnimationUtils.createCircularReveal(
                        cirRevealDst,
                        cirRevealDst.getWidth() / 2,
                        cirRevealDst.getHeight() / 2,
                        0,
                        Math.max(cirRevealDst.getWidth(), cirRevealDst.getHeight()));

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

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cirRevealDst.setVisibility(View.INVISIBLE);
                    }
                });

                anim.start();
            }
        });

        cirRevealNormal.setOnClickListener(v -> {
            Animator mNormalAnimator = ViewAnimationUtils.createCircularReveal(
                    cirRevealNormal,
                    cirRevealNormal.getWidth() / 2,
                    cirRevealNormal.getHeight() / 2,
                    0,
                    Math.max(cirRevealNormal.getWidth(), cirRevealNormal.getHeight()));
            mNormalAnimator.setInterpolator(new AccelerateInterpolator());
            mNormalAnimator.start();
        });

        cirRevealHypot.setOnClickListener(v -> {
            //勾股定理得到斜边长度
            float endRadius = (float) Math.hypot(cirRevealHypot.getWidth(),
                    cirRevealHypot.getHeight());
            Animator mHypotAnimator = ViewAnimationUtils.createCircularReveal(cirRevealHypot,
                    cirRevealHypot.getWidth(), 0, 0, endRadius);
            mHypotAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mHypotAnimator.start();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void makeSceneTransitionAnimationNoParameter(int flag) {
        Intent intent = new Intent(getActivity(), OneActivity.class);
        intent.putExtra("flag", flag);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
        ActivityCompat.startActivity(Objects.requireNonNull(getActivity()), intent, options.toBundle());
    }
}
