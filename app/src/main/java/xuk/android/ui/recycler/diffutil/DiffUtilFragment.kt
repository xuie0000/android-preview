package xuk.android.ui.recycler.diffutil


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import xuk.android.R

/**
 * https://github.com/mrmike/DiffUtil-sample
 *
 * @author xuie
 */
class DiffUtilFragment : Fragment() {
  private var adapter: ActorAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_diff_util, container, false)

    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    adapter = ActorAdapter(ActorRepository.actorListSortedByRating)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = adapter
    recyclerView.isNestedScrollingEnabled = false
    return view
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    menu.add(Menu.NONE, R.id.sort_by_name, Menu.NONE, "Sort by name").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.sort_by_rating, Menu.NONE, "Sort by rating").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.sort_by_birth, Menu.NONE, "Sort by year of birth").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.sort_by_name -> adapter!!.swapItems(ActorRepository.actorListSortedByName)
      R.id.sort_by_rating -> adapter!!.swapItems(ActorRepository.actorListSortedByRating)
      R.id.sort_by_birth -> adapter!!.swapItems(ActorRepository.actorListSortedByYearOfBirth)
      else -> adapter!!.swapItems(ActorRepository.actorListSortedByName)
    }
    return super.onOptionsItemSelected(item)
  }
}
