package xuk.android.ui.recycler.gridpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import xuk.android.R

/**
 * @author Jie Xu
 * @date 2019/11/12
 */
class NormalHolder(view: View) : BaseHolder(view) {

  private val head: ImageView = view.findViewById(R.id.iv_head)
  private val title: TextView = view.findViewById(R.id.tv_title)
  private val desc: TextView = view.findViewById(R.id.tv_desc)

  override fun bind(item: GridItem) {
    Glide.with(itemView.context).load(item.source)
        .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0)))
        .into(head)
    title.text = item.name
    desc.text = item.other
  }

  companion object {
    fun create(parent: ViewGroup): NormalHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_normal_recycle_item, parent, false)
      return NormalHolder(view)
    }
  }

}