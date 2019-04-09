package com.xuie.android.ui.recycler.discrete

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView

import com.xuie.android.R

/**
 * @author yarolegovich
 * @date 07.03.2017
 */
class ShopAdapter(private val data: List<Item>) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val v = inflater.inflate(R.layout.item_shop_card, parent, false)
    return ViewHolder(v)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //        Glide.with(holder.itemView.getContext())
    //                .load(data.get(position).getImage())
    //                .into(holder.image);
    holder.image.setImageResource(data[position].image)
  }

  override fun getItemCount(): Int {
    return data.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
  }
}
