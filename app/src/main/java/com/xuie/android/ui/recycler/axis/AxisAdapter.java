package com.xuie.android.ui.recycler.axis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuie.android.R;

import java.util.List;

/**
 * @author xuie
 * @date 17-8-9
 */

public class AxisAdapter extends RecyclerView.Adapter<AxisAdapter.MyViewHolder> {
    private List<DataBean> dataBeen;

    public AxisAdapter() {
        dataBeen = DataBean.getDataBeen();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_axis, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
            time = itemView.findViewById(R.id.tv_time);
            date = itemView.findViewById(R.id.tv_date);
            information = itemView.findViewById(R.id.information);
        }
    }
}
