package xuk.android.ui.recycler.gridpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import xuk.android.R

class SmallHolder(view: View) : BaseHolder(view) {

  private val head: ShapeableImageView = view.findViewById(R.id.iv_head)
  private val name: TextView = view.findViewById(R.id.tv_content)

  override fun bind(item: GridItem) {
    name.text = item.name
    head.setImageResource(item.source)
  }

  companion object {
    fun create(parent: ViewGroup): SmallHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_small_recycle_item, parent, false)
      return SmallHolder(view)
    }
  }

}