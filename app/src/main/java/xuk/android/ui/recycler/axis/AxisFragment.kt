package xuk.android.ui.recycler.axis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import xuk.android.R
import xuk.android.databinding.FragmentAxisBinding

/**
 * 这是一个时间轴的DEMO，添加的 [RecyclerView.ItemDecoration]样例
 */
class AxisFragment : Fragment(R.layout.fragment_axis) {
  private val binding: FragmentAxisBinding by viewBinding()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.recyclerView.apply {
      adapter = AxisAdapter()
      layoutManager = LinearLayoutManager(context)
      addItemDecoration(AxisItemDecoration())
    }
  }

}
