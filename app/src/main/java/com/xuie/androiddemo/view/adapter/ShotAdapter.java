package com.xuie.androiddemo.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.xuie.androiddemo.bean.Shot;
import com.xuie.androiddemo.model.ImageModelImpl;
import com.xuie.androiddemo.util.ScreenUtils;
import com.xuie.androiddemo.view.activity.ShotActivity;
import com.xuie.androiddemo.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

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
            holder.commentsCountText.setText(shot.getCommentsCount());
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
//                intent.putParcelableArrayListExtra("datas", (ArrayList<? extends Parcelable>) dataList);
                activity.startActivity(intent);
                /*
                想了半天，还是觉得不要这个动画...
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(activity, holder.shotImage, "shotImage");
                    activity.startActivity(intent, options.toBundle());
                } else {
                    activity.startActivity(intent);
                }
                */
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
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    public static List<Shot> removeDuplicateDataInOrder(List<Shot> newList, List<Shot> oldList) {
        for (int i = 0; i < 10; i++) {
            oldList.add(i, newList.get(i));
        }
        return oldList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
