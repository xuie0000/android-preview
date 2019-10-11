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
      "DiffUtil",
      "ItemDecoration(时间轴)",
      "滑动缩放",
      "分页Paging"
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val listAdapter = ListAdapter(android.R.layout.simple_list_item_1)
    listAdapter.setOnItemClickListener { _, _, position ->
      when (position) {
        0 -> findNavController().navigate(R.id.action_to_diff_util)
        1 -> findNavController().navigate(R.id.action_to_axis)
        2 -> findNavController().navigate(R.id.action_to_discrete)
        3 -> findNavController().navigate(R.id.action_to_paging)
        else -> throw Exception("no position")
      }
    }
    recyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
    }
  }

  private inner class ListAdapter
  /**
   * Instantiates a new List adapter.
   *
   * @param layoutResId the layout res id
   */
  internal constructor(@LayoutRes layoutResId: Int) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, members) {

    override fun convert(helper: BaseViewHolder, item: String) {
      helper.setText(android.R.id.text1, item)
    }
  }

}
