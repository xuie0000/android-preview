package xuk.android.ui.transitions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xuk.android.R


class TransitionsAdapter(private val click: (view: View, resId: Int, pos: Int) -> Unit) :
  ListAdapter<Int, TransitionsAdapter.ItemViewHolder>(diffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    return ItemViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_transitions_adapter, parent, false),
      click
    )
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(getItem(position), position)
  }

  companion object {
    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
      override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
      }

      @SuppressLint("DiffUtilEquals")
      override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
      }
    }
  }

  class ItemViewHolder(
    view: View,
    private val click: (view: View, resId: Int, pos: Int) -> Unit
  ) :
    RecyclerView.ViewHolder(view) {
    private val ivImage = itemView.findViewById<ImageView>(R.id.image)

    fun bind(resId: Int, position: Int) {
      ivImage.transitionName = resId.toString()
      ivImage.setImageResource(resId)

      itemView.setOnClickListener {
        click.invoke(ivImage, resId, position)
      }
    }
  }
}

