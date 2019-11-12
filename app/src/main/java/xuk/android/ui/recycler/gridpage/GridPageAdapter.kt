package xuk.android.ui.recycler.gridpage

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * @author Jie Xu
 * @date 2019/10/16
 */
class GridPageAdapter :
    ListAdapter<GridItem, BaseHolder>(gridDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
    return when (viewType) {
      GridItem.TYPE_NORMAL -> NormalHolder.create(parent, clickCallback)
      GridItem.TYPE_SMALL -> SmallHolder.create(parent)
      GridItem.TYPE_SPECIAL -> SpecialHolder.create(parent)
      else -> throw IllegalArgumentException("unknown view type $viewType")
    }
  }

  override fun onBindViewHolder(holder: BaseHolder, position: Int) {
    holder.bind(getItem(position))
  }

  override fun getItemViewType(position: Int): Int {
    return getItem(position).type
  }

  private var clickCallback: ((view: View, position: Int, grid: GridItem) -> Unit)? = null

  fun setOnItemClickListener(clickCallback: (view: View, position: Int, grid: GridItem) -> Unit) {
    this.clickCallback = clickCallback
  }

  companion object {
    private val gridDiffCallback = object : DiffUtil.ItemCallback<GridItem>() {
      override fun areItemsTheSame(oldItem: GridItem, newItem: GridItem): Boolean {
        return oldItem.type == newItem.type
      }

      @SuppressLint("DiffUtilEquals")
      override fun areContentsTheSame(oldItem: GridItem, newItem: GridItem): Boolean {
        return oldItem == newItem
      }
    }
  }

}
