package xuk.android.ui.recycler

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import xuk.android.R

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {

  private var members: List<String> = arrayListOf(
      "ItemDecoration",
      "Paging3",
      "GridLayout"
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val listAdapter = ShowAdapter { pos ->
      when (pos) {
        0 -> findNavController().navigate(R.id.action_to_axis)
        1 -> findNavController().navigate(R.id.action_to_paging)
        2 -> findNavController().navigate(R.id.action_to_grid_page)
        else -> throw IllegalArgumentException("no position")
      }
    }
    listAdapter.submitList(members)

    recyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
    }
  }

  private class ShowAdapter(private val click: (pos: Int) -> Unit) : ListAdapter<String, ItemViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false), click)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
      holder.bind(getItem(position), position)
    }

  }

  private class ItemViewHolder(view: View, private val click: (pos: Int) -> Unit) : RecyclerView.ViewHolder(view) {
    private val tvText = itemView.findViewById<TextView>(R.id.tv_text)

    fun bind(text: String, position: Int) {
      tvText.text = text

      itemView.setOnClickListener {
        click.invoke(position)
      }
    }
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
