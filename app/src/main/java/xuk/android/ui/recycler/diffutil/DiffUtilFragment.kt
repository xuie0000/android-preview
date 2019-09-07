package xuk.android.ui.recycler.diffutil

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_diff_util.*
import xuk.android.R

/**
 * https://github.com/mrmike/DiffUtil-sample
 *
 * @author Jie Xu
 */
class DiffUtilFragment : Fragment(R.layout.fragment_diff_util) {
  private lateinit var actorAdapter: ActorAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val actorAdapter = ActorAdapter(ActorRepository.actorListSortedByRating)
    recyclerView.apply {
      layoutManager = LinearLayoutManager(activity)
      adapter = actorAdapter
      isNestedScrollingEnabled = false
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    menu.add(Menu.NONE, R.id.sort_by_name, Menu.NONE, "Sort by name").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.sort_by_rating, Menu.NONE, "Sort by rating").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.sort_by_birth, Menu.NONE, "Sort by year of birth").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.sort_by_name -> actorAdapter.swapItems(ActorRepository.actorListSortedByName)
      R.id.sort_by_rating -> actorAdapter.swapItems(ActorRepository.actorListSortedByRating)
      R.id.sort_by_birth -> actorAdapter.swapItems(ActorRepository.actorListSortedByYearOfBirth)
    }
    return super.onOptionsItemSelected(item)
  }
}
