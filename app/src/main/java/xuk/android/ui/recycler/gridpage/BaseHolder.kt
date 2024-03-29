package xuk.android.ui.recycler.gridpage

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {

  abstract fun bind(item: GridItem)
}