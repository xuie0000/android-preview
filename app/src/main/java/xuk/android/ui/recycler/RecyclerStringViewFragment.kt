package xuk.android.ui.recycler

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import xuk.android.R

abstract class RecyclerStringViewFragment : Fragment(R.layout.fragment_recycler_view) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val listAdapter = ShowAdapter(
      click = { _, _, pos ->
        loadClick().invoke(pos)
      }
    )
    listAdapter.submitList(loadData())

    recyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
    }
  }

  abstract fun loadData(): List<String>
  abstract fun loadClick(): (pos: Int) -> Unit

}

class ShowAdapter(
  private val click: ((view: View, text: String, pos: Int) -> Unit)? = null,
  private val resource: Int? = null
) :
  ListAdapter<String, ItemViewHolder>(diffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    return ItemViewHolder(
      LayoutInflater.from(parent.context)
        .inflate(resource ?: R.layout.item_recycler_view, parent, false),
      click
    )
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(getItem(position), position)
  }

  companion object {
    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
      override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
      }

      @SuppressLint("DiffUtilEquals")
      override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
      }
    }
  }

}

class ItemViewHolder(
  view: View,
  private val click: ((view: View, text: String, pos: Int) -> Unit)? = null
) :
  RecyclerView.ViewHolder(view) {
  private val tvText = itemView.findViewById<TextView>(R.id.tv_text)

  fun bind(text: String, position: Int) {
    tvText.text = text
    tvText.transitionName = text

    itemView.setOnClickListener {
      click?.invoke(tvText, text, position)
    }
  }
}
