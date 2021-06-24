package xuk.android.ui.recycler.paging

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import xuk.android.R
import xuk.android.databinding.FragmentPagingBinding

/**
 * Paging3
 */
class PagingFragment : Fragment(R.layout.fragment_paging) {
  private val binding: FragmentPagingBinding by viewBinding()

  private val viewModel: CheeseViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Create adapter for the RecyclerView
    val adapter = CheeseAdapter()
    binding.cheeseList.adapter = adapter

    lifecycleScope.launchWhenCreated {
      viewModel.allCheeses.collectLatest {
        Timber.d("add item $it")
        adapter.submitData(it)
      }
    }

    initAddButtonListener()
    initSwipeToDelete()
  }

  private fun initSwipeToDelete() {
    ItemTouchHelper(object : ItemTouchHelper.Callback() {
      // enable the items to swipe to the left or right
      override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
      ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

      override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean = false

      // When an item is swiped, remove the item via the view model. The list item will be
      // automatically removed in response, because the adapter is observing the live list.
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        (viewHolder as CheeseViewHolder).cheese?.let {
          viewModel.remove(it)
        }
      }
    }).attachToRecyclerView(binding.cheeseList)
  }

  private fun addCheese() {
    val newCheese = binding.inputText.text.trim()
    if (newCheese.isNotEmpty()) {
      viewModel.insert(newCheese)
      binding.inputText.setText("")
    }
  }

  private fun initAddButtonListener() {
    binding.addButton.setOnClickListener {
      addCheese()
    }

    // when the user taps the "Done" button in the on screen keyboard, save the item.
    binding.inputText.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        addCheese()
        return@setOnEditorActionListener true
      }
      false // action that isn't DONE occurred - ignore
    }
    // When the user clicks on the button, or presses enter, save the item.
    binding.inputText.setOnKeyListener { _, keyCode, event ->
      if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
        addCheese()
        return@setOnKeyListener true
      }
      false // event that isn't DOWN or ENTER occurred - ignore
    }
  }

}
