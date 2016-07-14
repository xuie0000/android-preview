package com.xuie.androiddemo.ui.fragment.shots;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mikepenz.iconics.IconicsDrawable;
import com.xuie.androiddemo.App;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.dribbble.Shot;
import com.xuie.androiddemo.bean.dribbble.User;
import com.xuie.androiddemo.model.ImageModelImpl;
import com.xuie.androiddemo.ui.fragment.shots.presenter.ShotsPresenter;
import com.xuie.androiddemo.ui.fragment.BaseFragment;
import com.xuie.androiddemo.util.PreferenceUtils;
import com.xuie.androiddemo.util.ScreenUtils;
import com.xuie.androiddemo.ui.activity.shot.ShotActivity;
import com.xuie.androiddemo.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotsFragment extends BaseFragment<ShotsFragment, ShotsPresenter> implements IShotsFragment<Shot> {
    @BindView(R.id.content_recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.content_swipe) SwipeRefreshLayout mRefreshLayout;

    LinearLayoutManager mLayoutManager;
    ShotAdapter mShotAdapter;
    List<Shot> dataList;

    @Override protected ShotsPresenter createPresenter() {
        return new ShotsPresenter();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_content_shots, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        mPresenter.loadDataFromRealm();
    }

    public void init() {
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getActivity()));
        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.requestNewDate();
            mPresenter.updateUserInfo();
            showProgress();
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLayoutManager.findLastVisibleItemPosition() == mShotAdapter.dataList.size()) {
                    mPresenter.requestDate();
                }
            }
        });
    }

    @Override public void showShots(List<Shot> list, int current_page) {
        if (dataList == null) {
            dataList = list;
            mShotAdapter = new ShotAdapter(list, getActivity());
            mRecyclerView.setAdapter(mShotAdapter);
        } else {
            mShotAdapter.addMoreData(list, current_page);
        }
    }

    @Override public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override public void closeProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override public void uploadUserInfo(User user) {
    }

    @Override public void onResume() {
        super.onResume();
        int position = PreferenceUtils.getPrefInt(getActivity(), "current_position", -1);
        if (mShotAdapter != null && mShotAdapter.dataList != null && position != -1) {
            mShotAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(position);
        }
    }

    public class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        public List<Shot> dataList;
        private int pre_page = 1;
        private Activity activity;

        public ShotAdapter(List<Shot> dataList, Activity activity) {
            this.dataList = new ArrayList<>();
            this.dataList.addAll(dataList);
            this.activity = activity;
        }


        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view;
            if (viewType == TYPE_ITEM) {
                view = inflater.inflate(R.layout.recycle_shot_item, parent, false);
                view.setTag(TYPE_ITEM);
            } else {
                view = inflater.inflate(R.layout.recycle_foot_view, parent, false);
                view.setTag(TYPE_FOOTER);
            }
            return new ViewHolder(view);
        }


        @Override public int getItemViewType(int position) {
            // 最后一个item设置为footerView
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override public void onBindViewHolder(final ViewHolder holder, int position) {
            if (position < dataList.size()) {
                Shot shot = dataList.get(position);
                holder.commentsCountText.setText(String.format(Locale.CHINA, "%d ", shot.getCommentsCount()));
                holder.likeCountText.setText(String.format(Locale.CHINA, "%d 人喜欢这张照片。", shot.getLikesCount()));
                holder.usernameText.setText(shot.getUser().getUsername());
                // holder.viewsCountText.setText(shot.getViewsCount().toString());
                holder.mFavoriteImg.setOnClickListener(v -> {
                            if (!holder.isLike) {
                                holder.mFavoriteImg
                                        .setImageDrawable(new IconicsDrawable(App.getContext(), "gmi_favorite")
                                                .color(Color.parseColor("#FF4081"))
                                        );
                                holder.isLike = true;
                            } else {
                                holder
                                        .mFavoriteImg
                                        .setImageDrawable(new IconicsDrawable(App.getContext(), "gmi_favorite_outline")
                                                .color(Color.parseColor("#FF4081"))
                                        );
                                holder.isLike = false;
                            }

                        }
                );

                ViewGroup.LayoutParams params = holder.shotImage.getLayoutParams();
                params.height = (int) ScreenUtils.dpToPx(shot.getHeight());
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                holder.shotImage.setLayoutParams(params);


                ImageModelImpl.getInstance().loadImage(shot.getImages().getNormal(), holder.shotImage);
                ImageModelImpl.getInstance().loadImageWithTargetView(shot.getUser().getAvatarUrl(), new SimpleTarget<Bitmap>() {
                    @Override public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        holder.avatarImage.setImageBitmap(bitmap);
                    }
                });

                holder.shotImage.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, ShotActivity.class);
                    intent.putExtra("position", position);
                    activity.startActivity(intent);
                });

            }
        }

        @Override public int getItemCount() {
            return dataList.size() == 0 ? 0 : dataList.size() + 1;
        }

        public void addMoreData(List<Shot> datas, int current_page) {
            if (current_page == pre_page) {
                dataList = datas;
            } else {
                dataList.addAll(datas);
            }

            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            boolean isLike = false;

            @BindView(R.id.shot_image) ImageView shotImage;
            @BindView(R.id.avatar_image) CircleImageView avatarImage;
            @BindView(R.id.username_text) TextView usernameText;
            @BindView(R.id.commentsCount_text) TextView commentsCountText;
            @BindView(R.id.likeCount_text) TextView likeCountText;
            @BindView(R.id.shot_favorite) ImageView mFavoriteImg;

            ViewHolder(View view) {
                super(view);
                if ((int) view.getTag() == TYPE_ITEM)
                    ButterKnife.bind(this, view);
            }
        }
    }

}
