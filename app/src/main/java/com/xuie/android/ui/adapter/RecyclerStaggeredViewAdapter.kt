package com.xuie.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.orhanobut.logger.Logger
import com.xuie.android.R

/**
 * @author xuie
 */
class RecyclerStaggeredViewAdapter(textPictures: List<TextColor>) : RecyclerViewAdapter(textPictures) {

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    if (viewType == ITEM_TYPE_NORMAL) {
      return super.onCreateViewHolder(viewGroup, viewType)
    } else if (viewType == ITEM_TYPE_LARGER) {
      val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_text_row_larger, viewGroup, false)
      return RecyclerStaggeredViewHolder(v)
    } else {
      throw IllegalArgumentException("viewType is ERROR, ITEM_TYPE_LARGER, ITEM_TYPE_NORMAL")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is RecyclerViewAdapter.RecyclerViewHolder) {
      super.onBindViewHolder(holder, position)
    } else if (holder is RecyclerStaggeredViewHolder) {
      val textPicture = textPictures[position]

      holder.card.setCardBackgroundColor(textPicture.color)
      holder.title.text = textPicture.text
      holder.subText.text = textPicture.text
      startAnimation(holder.itemView)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) ITEM_TYPE_LARGER else ITEM_TYPE_NORMAL
    //        return position == getTopPosition() ? ITEM_TYPE_LARGER : ITEM_TYPE_NORMAL;
  }

  internal inner class RecyclerStaggeredViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var card: CardView
    var title: TextView
    var subText: TextView

    init {
      card = itemView as CardView
      title = itemView.findViewById(R.id.title)
      subText = itemView.findViewById(R.id.subText)
      itemView.setOnClickListener { v -> Logger.d("Element $adapterPosition clicked.") }
    }
  }

  companion object {
    private val ITEM_TYPE_NORMAL = 1
    private val ITEM_TYPE_LARGER = 2
  }

}
