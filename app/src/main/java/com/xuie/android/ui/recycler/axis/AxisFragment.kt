package com.xuie.android.ui.recycler.axis


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.xuie.android.R

/**
 * A simple [Fragment] subclass.
 * 这是一个时间轴的DEMO，添加的 [RecyclerView.ItemDecoration]样例
 *
 * @author xuie
 */
class AxisFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_axis, container, false)
    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.adapter = AxisAdapter()
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.addItemDecoration(AxisItemDecoration())
    return view
  }

}
