/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.xuie.androiddemo.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.TextPicture;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<TextPicture> textPictures;

    public RecyclerViewAdapter(List<TextPicture> textPictures) {
        this.textPictures = textPictures;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new NormalViewHolder(v);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TextPicture textPicture = textPictures.get(position);
        NormalViewHolder vh = ((NormalViewHolder) viewHolder);
        vh.button.setBackgroundResource(textPicture.getPicture());
        vh.button.setText(textPicture.getText());
    }

    @Override public int getItemCount() {
        return textPictures.size();
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.button) Button button;

        public NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            button.setOnClickListener(v -> Logger.d("Element " + getAdapterPosition() + " clicked."));
        }
    }

}
