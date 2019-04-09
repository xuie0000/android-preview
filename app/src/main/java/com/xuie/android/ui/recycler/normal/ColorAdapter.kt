package com.xuie.android.ui.recycler.normal

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.xuie.android.R
import com.xuie.android.provider.ColorContract

/**
 * @author Jie Xu
 * @date 2017/4/12 0012
 */
open class ColorAdapter(context: Context, cursor: Cursor?) : CursorRecyclerViewAdapter<RecyclerView.ViewHolder>(context, cursor) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text_row, parent, false)
    return ColorViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, cursor: Cursor) {
    val vh = viewHolder as ColorViewHolder

    vh.title.text = cursor.getString(NAME_INDEX)
    vh.card.setCardBackgroundColor(cursor.getInt(COLOR_INDEX))
    startAnimation(vh.itemView)
  }

  internal fun startAnimation(view: View) {
    val animators = arrayOf<Animator>(
        ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f),
        ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f)
    )

    for (anim in animators) {
      anim.setDuration(300).start()
    }
  }

  internal inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var card: CardView = itemView as CardView
    var title: TextView = itemView.findViewById(R.id.title)
  }

  companion object {
    private const val TAG = "ColorAdapter"
    /**
     * Column projection for the query to pull Colors from the database.
     */
    val MOVIE_COLUMNS = arrayOf(BaseColumns._ID, ColorContract.ColorEntry.COLUMN_NAME, ColorContract.ColorEntry.COLUMN_COLOR)

    internal const val NAME_INDEX = 1
    internal const val COLOR_INDEX = 2
  }
}
