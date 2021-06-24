package xuk.android.ui.recycler.card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import xuk.android.R
import xuk.android.databinding.FragmentCardBinding
import xuk.android.ui.recycler.ShowAdapter
import xuk.android.ui.recycler.card.widget.transformer.StackTransformer

/**
 * 卡片
 */
class CardFragment : Fragment(R.layout.fragment_card) {

  private val binding: FragmentCardBinding by viewBinding()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val adapter = ShowAdapter(resource = R.layout.item_card)

    val data = listOf(
      getString(R.string.attachment_summary_on),
      "page 1",
      "page 2",
      "page 3",
      "page 4",
      "page 5 \ncard switch ui",
      "page 6"
    )
    adapter.submitList(data)

    binding.viewPager.also { pager ->
      pager.adapter = adapter
      pager.offscreenPageLimit = 3

      pager.setPageTransformer(
        StackTransformer(stackLimit = 3)
      )
      pager.registerOnPageChangeCallback(onPageSnapListener)
    }


  }

  private val onPageSnapListener: OnPageSnapListener by lazy {
    OnPageSnapListener { previousPosition ->
      Timber.d("previousPosition:$previousPosition")
    }
  }

}
