package com.xuie.androiddemo.view.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.Shot;
import com.xuie.androiddemo.databinding.ContentShotBinding;
import com.xuie.androiddemo.model.ImageModelImpl;

public class SingleShotFragment extends BaseLazyFragment implements View.OnClickListener {
    public static final String SHOT = "shot";
    private Shot shot;
    private ContentShotBinding binding;
    private boolean isVisible;

//    public static SingleShotFragment newInstance(Shot shot) {
//        SingleShotFragment fragment = new SingleShotFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(SHOT, shot);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        shot = getArguments().getParcelable(SHOT);
//    }


    @SuppressLint("ValidFragment") public SingleShotFragment(Shot shot) {
        this.shot = shot;
    }

    public SingleShotFragment() {
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_shot, container, false);
        binding = DataBindingUtil.bind(view);

        init();
        return view;
    }

    private void init() {
        binding.headContent.imgShare.setOnClickListener(this);
        binding.headContent.imgBack.setOnClickListener(this);
    }

    @Override void onFirstUserVisible() {
        if (shot != null)
            setData();
    }


    @Override public void setData() {
        if (!isVisible) {
            showProgress();
            bindData();
        }
    }

    private void bindData() {
        binding.setShot(shot);
        ImageModelImpl.getInstance().loadImage(shot.getImages().getNormal(), binding.imageShot);
        isVisible = true;
        closeProgress();
    }


    @Override public void setData(Shot shot) {
        this.shot = shot;
        if (binding != null) {
            bindData();
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        isVisible = false;
    }

    @Override void onUserVisible() {
    }

    @Override void onFirstUserInvisible() {
    }

    @Override void onUserInvisible() {
    }

    @Override public void showProgress() {
        super.showProgress();
        binding.fragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override public void closeProgress() {
        super.closeProgress();
        binding.fragmentProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.img_share:
                break;
            case R.id.img_shot_favorite:
                break;
        }

    }
}
