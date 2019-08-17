package xuk.android.ui.recycler.normal

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_normal.*
import xuk.android.R
import xuk.android.provider.ColorContract
import xuk.android.ui.adapter.MarginDecoration
import java.util.*

/**
 * @author Jie Xu
 */
class NormalFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

  private var currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER
  private var currentItemType = ItemType.STAGGERED
  private lateinit var colorAdapter: ColorAdapter
  private lateinit var colorStaggeredAdapter: ColorAdapter

  /**
   * Grid ver manager layout manager type.
   */
  private enum class LayoutManagerType {
    GRID_VER_MANAGER,
    LINEAR_VER_MANAGER,
    LINEAR_HOR_MANAGER
  }

  /**
   * Single item type.
   */
  private enum class ItemType {
    SINGLE,
    STAGGERED
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_normal, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (savedInstanceState != null) {
      currentLayoutManagerType = savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
    }
    colorAdapter = ColorAdapter(context!!, null)
    colorStaggeredAdapter = ColorStaggeredAdapter(context!!, null)

    setRecyclerViewLayoutManager(currentLayoutManagerType)
    recyclerView.addItemDecoration(MarginDecoration())
    recyclerView.adapter = colorStaggeredAdapter
  }

  override fun onResume() {
    super.onResume()
    loaderManager.initLoader(0, null, this)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    menu.add(Menu.NONE, R.id.linear_v_single, Menu.NONE, "Linear & Vertical & Single").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.linear_h_single, Menu.NONE, "Linear & Horizontal & Single").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.grid_v_single, Menu.NONE, "Grid & Vertical & Single").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
    menu.add(Menu.NONE, R.id.grid_h_stagger, Menu.NONE, "Grid & Horizontal & Stagger").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.linear_v_single -> {
        currentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER
        currentItemType = ItemType.SINGLE
      }
      R.id.linear_h_single -> {
        currentLayoutManagerType = LayoutManagerType.LINEAR_HOR_MANAGER
        currentItemType = ItemType.SINGLE
      }
      R.id.grid_v_single -> {
        currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER
        currentItemType = ItemType.SINGLE
      }
      R.id.grid_h_stagger -> {
        currentLayoutManagerType = LayoutManagerType.GRID_VER_MANAGER
        currentItemType = ItemType.STAGGERED
      }
      else -> {
        currentLayoutManagerType = LayoutManagerType.LINEAR_VER_MANAGER
        currentItemType = ItemType.SINGLE
      }
    }
    setRecyclerViewLayoutManager(currentLayoutManagerType)
    if (currentItemType == ItemType.SINGLE) {
      refreshAdapter(colorAdapter)
    } else if (currentItemType == ItemType.STAGGERED) {
      refreshAdapter(colorStaggeredAdapter)
    }
    return super.onOptionsItemSelected(item)
  }

  private fun setRecyclerViewLayoutManager(type: LayoutManagerType) {
    // 记录切换前第一个可视视图位置
    var firstPosition = 0
    var layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager
    if (layoutManager is LinearLayoutManager) {
      firstPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    } else if (layoutManager is StaggeredGridLayoutManager) {
      val firstPositions = IntArray(layoutManager.spanCount)
      layoutManager.findFirstCompletelyVisibleItemPositions(firstPositions)
      firstPosition = findMax(firstPositions)
    }

    when (type) {
      LayoutManagerType.GRID_VER_MANAGER -> if (currentItemType != ItemType.STAGGERED) {
        layoutManager = GridLayoutManager(activity, SPAN_COUNT)
      } else {
        layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
      }
      LayoutManagerType.LINEAR_VER_MANAGER -> layoutManager = LinearLayoutManager(activity)
      LayoutManagerType.LINEAR_HOR_MANAGER -> layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    recyclerView.layoutManager = layoutManager
    recyclerView.scrollToPosition(firstPosition)
    recyclerView.isNestedScrollingEnabled = false
  }

  private fun findMax(lastPositions: IntArray): Int {
    var max = lastPositions[0]
    for (value in lastPositions) {
      if (value > max) {
        max = value
      }
    }
    return max
  }

  override fun onSaveInstanceState(savedInstanceState: Bundle) {
    savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType)
    super.onSaveInstanceState(savedInstanceState)
  }

  override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
    when (id) {
      0 -> return CursorLoader(
          Objects.requireNonNull<Context>(context),
          ColorContract.ColorEntry.CONTENT_URI,
          ColorAdapter.MOVIE_COLUMNS, null, null, null
      )
      else -> throw UnsupportedOperationException("Unknown loader id: $id")
    }
  }

  override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
    when (loader.id) {
      0 -> {
        colorAdapter!!.swapCursor(data)
        colorStaggeredAdapter!!.swapCursor(data)
      }
      else -> throw UnsupportedOperationException("Unknown loader id: " + loader.id)
    }
  }

  override fun onLoaderReset(loader: Loader<Cursor>) {
    when (loader.id) {
      0 -> {
        colorAdapter!!.swapCursor(null)
        colorStaggeredAdapter!!.swapCursor(null)
      }
      else -> throw UnsupportedOperationException("Unknown loader id: " + loader.id)
    }
  }

  private fun refreshAdapter(adapter: RecyclerView.Adapter<*>?) {
    recyclerView.adapter = adapter
  }

  companion object {
    const val KEY_LAYOUT_MANAGER = "layoutManager"

    const val SPAN_COUNT = 2
  }

}
