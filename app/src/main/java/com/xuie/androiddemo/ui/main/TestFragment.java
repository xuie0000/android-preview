package com.xuie.androiddemo.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.widget.ClearableEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {
    @BindView(R.id.shake_view) ImageView shakeView;
    @BindView(R.id.email) ClearableEditText email;
    @BindView(R.id.email_text_input_layout) TextInputLayout emailTextInputLayout;
    @BindView(R.id.password) ClearableEditText password;
    @BindView(R.id.password_text_input_layout) TextInputLayout passwordTextInputLayout;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shakeView.setImageDrawable(new IconicsDrawable(getActivity(), MaterialDesignIconic.Icon.gmi_view_carousel).color(Color.BLUE).sizeDp(24));
        shakeView.setOnClickListener(v -> shakeView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake)));
    }

}
