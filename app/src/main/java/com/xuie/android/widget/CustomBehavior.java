package com.xuie.android.widget;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by xuie on 16-9-6.
 * From Drakeet <drakeet.me@gmail.com> Meizhi
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private static final String TAG = "CustomBefavior";
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;
    private boolean mIsScrollUp = false;

    private float previousY = 0f;

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
//        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//    }
//
//    @Override public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
////        Log.d(TAG, "dxConsumed:" + dxConsumed + ", dyConsumed:" + dyConsumed + ", dxUnconsumed:" + dxUnconsumed + ", dyUnconsumed:" + dyUnconsumed);
//        if (dyUnconsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
//            // User scrolled down and the FAB is currently visible -> hide the FAB
//            animateOut(child);
//        } else if (dyUnconsumed < 0 && child.getVisibility() != View.VISIBLE) {
//            // User scrolled up and the FAB is currently not visible -> show the FAB
//            animateIn(child);
//        }
//
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        float y = dependency.getY();
//        Log.d(TAG, "onDependentViewChanged: " + y + ", " + previousY + ", " + (child.getVisibility() == View.VISIBLE));
        if (y - previousY > 0 && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        } else if (y - previousY < 0 && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        }

        previousY = dependency.getY();
        return true;
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private void animateOut(final FloatingActionButton button) {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button)
                    .scaleX(0.0F)
                    .scaleY(0.0F)
                    .alpha(0.0F)
                    .setInterpolator(INTERPOLATOR)
                    .withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        public void onAnimationStart(View view) {
                            CustomBehavior.this.mIsAnimatingOut = true;
                        }

                        public void onAnimationCancel(View view) {
                            CustomBehavior.this.mIsAnimatingOut = false;
                        }

                        public void onAnimationEnd(View view) {
                            CustomBehavior.this.mIsAnimatingOut = false;
                            view.setVisibility(View.GONE);
                        }
                    })
                    .start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.support.design.R.anim.design_fab_out);
            anim.setInterpolator(INTERPOLATOR);
            anim.setDuration(200L);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    CustomBehavior.this.mIsAnimatingOut = true;
                }

                public void onAnimationEnd(Animation animation) {
                    CustomBehavior.this.mIsAnimatingOut = false;
                    button.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                }
            });
            button.startAnimation(anim);
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button)
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .alpha(1.0F)
                    .setInterpolator(INTERPOLATOR)
                    .withLayer()
                    .setListener(null)
                    .start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.support.design.R.anim.design_fab_in);
            anim.setDuration(200L);
            anim.setInterpolator(INTERPOLATOR);
            button.startAnimation(anim);
        }
    }
}
