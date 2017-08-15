package com.xuie.android.ui.recyclerView.axis;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuie.android.R;

import java.util.List;

/**
 * Created by xuie on 17-8-9.
 */

public class AxisAdapter extends RecyclerView.Adapter<AxisAdapter.MyViewHolder> {
    private List<DataBean> dataBeen;

    public AxisAdapter() {
        dataBeen = DataBean.getDataBeen();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_axis, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataBean d = dataBeen.get(position);
        holder.time.setText(d.time);
        holder.date.setText(d.date);
        holder.information.setText(d.information);
    }

    @Override
    public int getItemCount() {
        return dataBeen.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView date;
        private TextView information;


        MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            information = (TextView) itemView.findViewById(R.id.information);
        }
    }
}
