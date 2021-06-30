package xuk.android.ui.recycler.offset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xuk.android.databinding.ItemScrollOffsetBinding

class ScrollOffsetAdapter : ListAdapter<Int, ScrollOffsetAdapter.ScrollOffsetViewHolder>(diffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollOffsetViewHolder {
    return ScrollOffsetViewHolder(ItemScrollOffsetBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: ScrollOffsetViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ScrollOffsetViewHolder(private val binding: ItemScrollOffsetBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(resource: Int) {
      binding.root.setImageResource(resource)
    }
  }

  private var clickCallback: ((view: View, position: Int, grid: ScrollOffsetViewHolder) -> Unit)? = null

  fun setOnItemClickListener(clickCallback: (view: View, position: Int, grid: ScrollOffsetViewHolder) -> Unit) {
    this.clickCallback = clickCallback
  }

  companion object {
    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
      override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
      }
    }
  }

}
