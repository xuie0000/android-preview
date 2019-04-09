package com.xuie.android.ui.recycler

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
import com.xuie.android.R
import com.xuie.android.ui.recycler.axis.AxisFragment
import com.xuie.android.ui.recycler.diffutil.DiffUtilFragment
import com.xuie.android.ui.recycler.discrete.DiscreteFragment
import com.xuie.android.ui.recycler.normal.NormalFragment

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 *
 * @author xuie
 */
class RecyclerViewFragment : Fragment() {

  private var members: MutableList<Member>? = null
  private var rootView: View? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    members = ArrayList()
    members!!.add(Member("样例", NormalFragment::class.java!!.getName()))
    members!!.add(Member("DiffUtil", DiffUtilFragment::class.java!!.getName()))
    members!!.add(Member("ItemDecoration(时间轴)", AxisFragment::class.java!!.getName()))
    members!!.add(Member("滑动缩放", DiscreteFragment::class.java!!.getName()))
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)

    val recyclerView = rootView!!.findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(context)
    val listAdapter = ListAdapter(android.R.layout.simple_list_item_1)
    listAdapter.setOnItemClickListener { adapter, view, position ->
      when (position) {
        0 -> Navigation.findNavController(rootView!!).navigate(R.id.action_to_normal)
        1 -> Navigation.findNavController(rootView!!).navigate(R.id.action_to_diff_util)
        2 -> Navigation.findNavController(rootView!!).navigate(R.id.action_to_axis)
        3 -> Navigation.findNavController(rootView!!).navigate(R.id.action_to_discrete)
        else -> Navigation.findNavController(rootView!!).navigate(R.id.action_to_normal)
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
  internal constructor(@LayoutRes layoutResId: Int) : BaseQuickAdapter<Member, BaseViewHolder>(layoutResId, members) {

    override fun convert(helper: BaseViewHolder, item: Member) {
      helper.setText(android.R.id.text1, item.name)
    }
  }

  private inner class Member
  /**
   * Instantiates a new Member.
   *
   * @param name     the name
   * @param fragment the fragment
   */
  internal constructor(
      /**
       * The Name.
       */
      var name: String,
      /**
       * The Fragment.
       */
      var fragment: String)

}
