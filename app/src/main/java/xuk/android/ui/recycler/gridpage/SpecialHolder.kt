package xuk.android.ui.recycler.gridpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import xuk.android.R

class SpecialHolder(view: View) : BaseHolder(view) {

  private val head: ShapeableImageView = view.findViewById(R.id.iv_head)
  private var title: TextView = view.findViewById(R.id.tv_title)

  override fun bind(item: GridItem) {
    head.setImageResource(item.source)
    title.text = item.name
  }


  companion object {
    fun create(parent: ViewGroup): SpecialHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_special_recycle_item, parent, false)
      return SpecialHolder(view)
    }
  }

}