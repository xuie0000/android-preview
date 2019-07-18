package xuk.android.ui.recycler.normal

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import xuk.android.R

/**
 * @author Jie Xu
 * @date 2017/4/12 0012
 */
class ColorStaggeredAdapter(context: Context, cursor: Cursor?) : ColorAdapter(context, cursor) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text_row_larger, parent, false)
    return when (viewType) {
      ITEM_TYPE_NORMAL -> super.onCreateViewHolder(parent, viewType)
      ITEM_TYPE_LARGER -> ColorStaggeredViewHolder(view)
      else -> ColorStaggeredViewHolder(view)
    }
  }

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, cursor: Cursor) {
    val vt = getItemViewType(viewHolder.adapterPosition)
    if (vt == ITEM_TYPE_NORMAL) {
      super.onBindViewHolder(viewHolder, cursor)
      return
    }
    val vh = viewHolder as ColorStaggeredViewHolder

    vh.title.text = cursor.getString(ColorAdapter.NAME_INDEX)
    vh.subText.text = cursor.getString(ColorAdapter.NAME_INDEX)
    vh.card.setCardBackgroundColor(cursor.getInt(ColorAdapter.COLOR_INDEX))
    startAnimation(vh.itemView)
  }

  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) ITEM_TYPE_LARGER else ITEM_TYPE_NORMAL
  }

  internal inner class ColorStaggeredViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var card: CardView = itemView as CardView
    var title: TextView = view.findViewById(R.id.title)
    var subText: TextView = view.findViewById(R.id.subText)
  }

  companion object {
    private const val TAG = "ColorStaggeredAdapter"
    private const val ITEM_TYPE_NORMAL = 0
    private const val ITEM_TYPE_LARGER = 1
  }
}
