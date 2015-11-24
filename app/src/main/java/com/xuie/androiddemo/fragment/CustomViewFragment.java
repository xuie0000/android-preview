package com.xuie.androiddemo.fragment;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.ui.ShowActivity;
import com.xuie.androiddemo.view.RoundProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomViewFragment extends Fragment {

    @Bind(R.id.bar1)
    RoundProgressBar bar1;
    @Bind(R.id.bar2)
    RoundProgressBar bar2;
    @Bind(R.id.bar3)
    RoundProgressBar bar3;
    private Context context;

    @Bind(R.id.view_group)
    LinearLayout viewGroup;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getActivity(), ShowActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), viewGroup, "share01");
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

    public CustomViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_view, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);

        bar1.setMaxTimes(200);
        bar1.setTimes(132);

        bar2.setMaxTimes(300);
        bar2.setTimes(300);

        bar3.setMaxTimes(100);
        bar3.setTimes(19);
        return view;
    }

    @OnTouch({R.id.view_group})
    public boolean onTouch(View v, final MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.d("xuie", "MotionEvent.ACTION_DOWN");
            v.animate().cancel();
            v.animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
//                    .translationY(v.getHeight() / 4) // Translates by 1/4 of the view's height to compensate the scale
//                    .setDuration(750)
                    .start();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            Log.d("xuie", "MotionEvent.ACTION_UP");
            v.animate().cancel();
            v.animate()
                    .scaleX(1f)
                    .scaleY(1f)
//                    .translationY(v.getHeight() / 4) // Translates by 1/4 of the view's height to compensate the scale
//                    .setDuration(750)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            Log.d("xuie", "onAnimationStart");
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Log.d("xuie", "onAnimationEnd");
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                Log.d("xuie", "onAnimationEnd 222");
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            Log.d("xuie", "onAnimationCancel");
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            Log.d("xuie", "onAnimationRepeat");
                        }
                    })
                    .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Log.d("xuie", "onAnimationUpdate");
                        }
                    })
                    .start();
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 300L);
        }

        return false;
    }

    @OnClick({R.id.view_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_group:
                Log.d("xuie", "view group onClick");
//                Intent intent = new Intent(getActivity(), ShowActivity.class);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "share01");
//                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
