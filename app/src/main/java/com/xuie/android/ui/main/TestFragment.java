package com.xuie.android.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.xuie.android.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * @author xuie
 */
public class TestFragment extends Fragment {
    private ImageView shakeView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        shakeView = view.findViewById(R.id.shake_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shakeView.setImageDrawable(new IconicsDrawable(Objects.requireNonNull(getActivity()), MaterialDesignIconic.Icon.gmi_view_carousel).color(Color.BLUE).sizeDp(24));
        shakeView.setOnClickListener(v -> shakeView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake)));
    }

}
