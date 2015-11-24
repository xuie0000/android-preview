package com.xuie.androiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuie.androiddemo.R;

/**
 * Created by xuie on 15-11-19.
 */
public class RecyclerStaggeredViewAdapter extends RecyclerViewAdapter {
    int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;
    int topPosition = 0;

    public RecyclerStaggeredViewAdapter(Context context, String[] dataSet, int[] backgroundSet) {
        super(context, dataSet, backgroundSet);
    }

    public static class LargerViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView subTextView;
        private ImageView imageView;

        public LargerViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RecyclerStaggeredViewAdapter", "Element " + getPosition() + " clicked.");
                }
            });
            imageView = (ImageView) v.findViewById(R.id.imageView);
            textView = (TextView) v.findViewById(R.id.textView);
            subTextView = (TextView) v.findViewById(R.id.subTextView);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getSubTextView() {
            return subTextView;
        }
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == ITEM_TYPE_NORMAL) {
            v = mLayoutInflater.inflate(R.layout.text_row_item, viewGroup, false);
        } else {
            v = mLayoutInflater.inflate(R.layout.text_row_larger_item, viewGroup, false);
        }

        return new NormalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("RecyclerStaggeredViewAd", "onBindViewHolder");
        if (viewHolder instanceof NormalViewHolder) {
            super.onBindViewHolder(viewHolder, position);
        } else if (viewHolder instanceof LargerViewHolder) {
            ((LargerViewHolder) viewHolder).getImageView().setBackgroundResource(mBackgroundSet[position]);
            ((LargerViewHolder) viewHolder).getTextView().setText(mDataSet[position]);
            ((LargerViewHolder) viewHolder).getSubTextView().setText(mDataSet[position]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
//        return position == getTopPosition() ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
    }

    public int getTopPosition() {
        return topPosition;
    }

    public void setTopPosition(int topPosition) {
        this.topPosition = topPosition;
    }

}
