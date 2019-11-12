package xuk.android.ui.recycler.gridpage

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Jie Xu
 * @date 2019/11/12
 */
abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {

  abstract fun bind(item: GridItem)
}