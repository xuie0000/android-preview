package xuk.android.ui.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xuk.android.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class RecyclerViewFragment : Fragment() {

  private lateinit var members: MutableList<String>
  private lateinit var rootView: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    members = ArrayList()
    members.add("样例")
    members.add("DiffUtil")
    members.add("ItemDecoration(时间轴)")
    members.add("滑动缩放")
    members.add("分页Paging")
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)

    val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(context)
    val listAdapter = ListAdapter(android.R.layout.simple_list_item_1)
    listAdapter.setOnItemClickListener { _, _, position ->
      when (position) {
        0 -> Navigation.findNavController(rootView).navigate(R.id.action_to_normal)
        1 -> Navigation.findNavController(rootView).navigate(R.id.action_to_diff_util)
        2 -> Navigation.findNavController(rootView).navigate(R.id.action_to_axis)
        3 -> Navigation.findNavController(rootView).navigate(R.id.action_to_discrete)
        4 -> Navigation.findNavController(rootView).navigate(R.id.action_to_paging)
        else -> Navigation.findNavController(rootView).navigate(R.id.action_to_normal)
      }
    }
    recyclerView.adapter = listAdapter

    return rootView
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
