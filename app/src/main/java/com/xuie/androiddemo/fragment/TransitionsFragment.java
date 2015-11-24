package com.xuie.androiddemo.fragment;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.ui.Show2Activity;
import com.xuie.androiddemo.ui.ShowActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransitionsFragment extends Fragment {

    @Bind(R.id.make_scene_transition_animation)
    Button makeSceneTransitionAnimation;
    @Bind(R.id.fab_button)
    Button fabButton;
    @Bind(R.id.over_shoot)
    ImageView overShoot;

    public TransitionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transitions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.make_scene_transition_animation, R.id.fab_button,
            R.id.explode, R.id.slide, R.id.fade, R.id.over_shoot})
    public void onClick(View view) {
        Intent intent;
        ActivityOptions options;
        switch (view.getId()) {
            case R.id.make_scene_transition_animation:
                Log.d("xuie", "make_scene_transition_animation");
                intent = new Intent(getActivity(), ShowActivity.class);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), makeSceneTransitionAnimation, "share01");

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
            case R.id.fab_button:
                Log.d("xuie", "fab_button");
                intent = new Intent(getActivity(), ShowActivity.class);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        // 创建多个共享元素
                        Pair.create((View) makeSceneTransitionAnimation, "share01"),
                        Pair.create((View) fabButton, "share02")
                );

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
            case R.id.explode:
                Log.d("xuie", "explode");
                intent = new Intent(getActivity(), ShowActivity.class);
                intent.putExtra("flag", 0);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
            case R.id.slide:
                Log.d("xuie", "slide");
                intent = new Intent(getActivity(), ShowActivity.class);
                intent.putExtra("flag", 1);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                getActivity().startActivity(intent, options.toBundle());
                break;
            case R.id.fade:
                Log.d("xuie", "fade");
                intent = new Intent(getActivity(), ShowActivity.class);
                intent.putExtra("flag", 2);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                getActivity().startActivity(intent, options.toBundle());
                break;
            case R.id.over_shoot:
                Log.d("xuie", "over_shoot");
                intent = new Intent(getActivity(), Show2Activity.class);
                options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        overShoot, "shareOverShoot");
//                Slide slide = new Slide();
//                slide.setInterpolator(new OvershootInterpolator());
//                slide.setDuration(1000l);
//                getActivity().getWindow().setEnterTransition(slide);
//                getActivity().getWindow().setExitTransition(slide);
//                options = ActivityOptions.makeSceneTransitionAnimation(getActivity());

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
        }
    }

    private Transition exitTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new BounceInterpolator());
        bounds.setDuration(2000);

        return bounds;
    }

    private Transition reenterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new OvershootInterpolator());
        bounds.setDuration(2000);

        return bounds;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
