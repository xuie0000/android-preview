package xuk.android.ui.recycler.axis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_axis.*

import xuk.android.R

/**
 * A simple [Fragment] subclass.
 * 这是一个时间轴的DEMO，添加的 [RecyclerView.ItemDecoration]样例
 *
 * @author Jie Xu
 */
class AxisFragment : Fragment(R.layout.fragment_axis) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recyclerView.apply {
      adapter = AxisAdapter()
      layoutManager = LinearLayoutManager(context)
      addItemDecoration(AxisItemDecoration())
    }
  }

}
