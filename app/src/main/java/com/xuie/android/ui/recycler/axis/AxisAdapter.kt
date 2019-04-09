package com.xuie.android.ui.recycler.axis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.xuie.android.R

/**
 * @author xuie
 * @date 17-8-9
 */

class AxisAdapter : RecyclerView.Adapter<AxisAdapter.MyViewHolder>() {
  private val dataBeen: List<DataBean>?

  init {
    dataBeen = DataBean.getDataBeen()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.item_axis, parent, false)
    return MyViewHolder(v)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val d = dataBeen!![position]
    holder.time.text = d.time
    holder.date.text = d.date
    holder.information.text = d.information
  }

  override fun getItemCount(): Int {
    return dataBeen!!.size
  }

  internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val time: TextView
    private val date: TextView
    private val information: TextView


    init {
      time = itemView.findViewById(R.id.tv_time)
      date = itemView.findViewById(R.id.tv_date)
      information = itemView.findViewById(R.id.information)
    }
  }
}
