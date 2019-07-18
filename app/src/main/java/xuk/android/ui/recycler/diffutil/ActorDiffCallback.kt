package xuk.android.ui.recycler.diffutil

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Jie Xu
 */
class ActorDiffCallback(private val oldList: List<Actor>, private val newList: List<Actor>) : DiffUtil.Callback() {

  override fun getOldListSize(): Int {
    return oldList.size
  }

  override fun getNewListSize(): Int {
    return newList.size
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldList[oldItemPosition].id == newList[newItemPosition].id
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldItem = oldList[oldItemPosition]
    val newItem = newList[newItemPosition]

    return oldItem.name == newItem.name
  }

  override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
    // Implement method if you're going to use ItemAnimator
    return super.getChangePayload(oldItemPosition, newItemPosition)
  }
}
