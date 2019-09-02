package xuk.android.ui.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
class RecyclerViewFragment : Fragment() {

  private var members: List<String> = arrayListOf(
      "样例",
      "DiffUtil",
      "ItemDecoration(时间轴)",
      "滑动缩放",
      "分页Paging"
  )

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

    val listAdapter = ListAdapter(android.R.layout.simple_list_item_1)
    listAdapter.setOnItemClickListener { _, _, position ->
      when (position) {
        0 -> Navigation.findNavController(view).navigate(R.id.action_to_normal)
        1 -> Navigation.findNavController(view).navigate(R.id.action_to_diff_util)
        2 -> Navigation.findNavController(view).navigate(R.id.action_to_axis)
        3 -> Navigation.findNavController(view).navigate(R.id.action_to_discrete)
        4 -> Navigation.findNavController(view).navigate(R.id.action_to_paging)
        else -> Navigation.findNavController(view).navigate(R.id.action_to_normal)
      }
    }
    recyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
    }

    return view
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
