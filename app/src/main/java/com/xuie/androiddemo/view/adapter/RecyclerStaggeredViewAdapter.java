package com.xuie.androiddemo.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.TextPicture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuie on 15-11-19.
 */
public class RecyclerStaggeredViewAdapter extends RecyclerViewAdapter {
    int ITEM_TYPE_NORMAL = 1, ITEM_TYPE_LARGER = 2;

    public RecyclerStaggeredViewAdapter(List<TextPicture> textPictures) {
        super(textPictures);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return super.onCreateViewHolder(viewGroup, viewType);
        } else if (viewType == ITEM_TYPE_LARGER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_larger_item, viewGroup, false);
            return new LargerViewHolder(v);
        } else {
            throw new IllegalArgumentException("viewType is ERROR, ITEM_TYPE_LARGER, ITEM_TYPE_NORMAL");
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NormalViewHolder) {
            super.onBindViewHolder(viewHolder, position);
        } else if (viewHolder instanceof LargerViewHolder) {
            TextPicture textPicture = textPictures.get(position);

            LargerViewHolder vh = ((LargerViewHolder) viewHolder);
            vh.button.setBackgroundResource(textPicture.getPicture());
            vh.button.setText(textPicture.getText());
            vh.subText.setText(textPicture.getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
//        return position == getTopPosition() ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
    }

    public class LargerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.button) Button button;
        @BindView(R.id.subText) TextView subText;

        public LargerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> Logger.d("Element " + getAdapterPosition() + " clicked."));
        }
    }

}
