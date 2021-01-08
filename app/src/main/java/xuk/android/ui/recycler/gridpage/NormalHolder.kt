package xuk.android.ui.recycler.gridpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import xuk.android.R

class NormalHolder(view: View,
                   private val clickCallback: ((view: View, position: Int, item: GridItem) -> Unit)?
) : BaseHolder(view) {

  private val head: ShapeableImageView = view.findViewById(R.id.iv_head)
  private val title: TextView = view.findViewById(R.id.tv_title)
  private val desc: TextView = view.findViewById(R.id.tv_desc)

  override fun bind(item: GridItem) {
    title.text = item.name
    desc.text = item.other
    head.setImageResource(item.source)
    itemView.setOnClickListener {
      clickCallback?.invoke(it, adapterPosition, item)
    }
  }

  companion object {
    fun create(parent: ViewGroup, clickCallback: ((view: View, position: Int, item: GridItem) -> Unit)?): NormalHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_normal_recycle_item, parent, false)
      return NormalHolder(view, clickCallback)
    }
  }

}