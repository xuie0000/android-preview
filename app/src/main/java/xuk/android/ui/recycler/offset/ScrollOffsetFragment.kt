package xuk.android.ui.recycler.offset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import xuk.android.R
import xuk.android.databinding.FragmentScrollOffsetBinding

class ScrollOffsetFragment : Fragment(R.layout.fragment_scroll_offset) {
  private val binding: FragmentScrollOffsetBinding by viewBinding()

  private val ids = listOf(R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four)
  private val adapter by lazy { ScrollOffsetAdapter() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

binding.viewPager.apply {
  adapter = this@ScrollOffsetFragment.adapter
  offscreenPageLimit = 3
  setPageTransformer(ScrollOffsetTransformer())
}
    adapter.submitList(ids)

  }

}