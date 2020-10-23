package xuk.android.ui.recycler

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import xuk.android.R

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {

  private var members: List<String> = arrayListOf(
      "ItemDecoration(时间轴)",
      "滑动缩放",
      "分页Paging",
      "Grid Page"
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val listAdapter = ListAdapter(android.R.layout.simple_list_item_1)
    listAdapter.setOnItemClickListener { _, _, position ->
      when (position) {
        0 -> findNavController().navigate(R.id.action_to_axis)
        1 -> findNavController().navigate(R.id.action_to_discrete)
        2 -> findNavController().navigate(R.id.action_to_paging)
        3 -> findNavController().navigate(R.id.action_to_grid_page)
        else -> throw IllegalArgumentException("no position")
      }
    }
    recyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
    }
  }

  private inner class ListAdapter(@LayoutRes layoutResId: Int)
    : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, members) {

    override fun convert(helper: BaseViewHolder, item: String) {
      helper.setText(android.R.id.text1, item)
    }
  }

}
