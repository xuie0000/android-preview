package xuk.android.ui.pager2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_page.*
import xuk.android.R

class PageFragment : Fragment(R.layout.fragment_page) {

  companion object {
    private const val ARG_SECTION_NUMBER = "section_number"
    @JvmStatic
    fun newInstance(sectionNumber: Int): PageFragment {
      return PageFragment().apply {
        arguments = Bundle().apply {
          putInt(ARG_SECTION_NUMBER, sectionNumber)
        }
      }
    }
  }

  private val viewModel: PageViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
    viewModel.text.observe(this, Observer<String> {
      section_label.text = it
    })
  }

}
