package xuk.android.ui.recycler.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_card.*
import timber.log.Timber
import xuk.android.R
import xuk.android.ui.recycler.ShowAdapter
import xuk.android.ui.recycler.card.widget.transformer.StackTransformer

/**
 * 卡片
 */
class CardFragment : Fragment(R.layout.fragment_card) {

  @SuppressLint("WrongConstant")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val adapter = ShowAdapter(resource = R.layout.item_card)

    val data = listOf(
      getString(R.string.attachment_summary_on),
      "page 1",
      "page 2",
      "page 3",
      "page 4",
      "page 5 \n",
      "page 6"
    )
    adapter.submitList(data)

    viewPager.also { pager ->
      pager.adapter = adapter
      pager.offscreenPageLimit = STACK_LIMIT

      pager.setPageTransformer(
        StackTransformer(stackLimit = STACK_LIMIT)
      )
      pager.registerOnPageChangeCallback(onPageSnapListener)
    }


  }

  private val onPageSnapListener: OnPageSnapListener by lazy {
    OnPageSnapListener { previousPosition ->
      Timber.d("previousPosition:$previousPosition")
    }
  }

  companion object {
    const val STACK_LIMIT: Int = 3
  }

}
