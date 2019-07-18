package xuk.android.ui.recycler.discrete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import xuk.android.R
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.Orientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class DiscreteFragment : Fragment(), DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder> {

  private var infiniteAdapter: InfiniteScrollAdapter<*>? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_discrete, container, false)

    val itemPicker = view.findViewById<DiscreteScrollView>(R.id.item_picker)

    val shop = Shop.get()
    val data = shop.data
    itemPicker.setOrientation(Orientation.HORIZONTAL)
    itemPicker.addOnItemChangedListener(this)
    infiniteAdapter = InfiniteScrollAdapter.wrap<ShopAdapter.ViewHolder>(ShopAdapter(data))
    itemPicker.adapter = infiniteAdapter
    itemPicker.setSlideOnFling(true)
    itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.transitionTime)
    itemPicker.setItemTransformer(ScaleTransformer.Builder()
        .setMinScale(0.8f)
        .build())

    return view
  }

  override fun onCurrentItemChanged(viewHolder: RecyclerView.ViewHolder?, adapterPosition: Int) {
    val positionInDataSet = infiniteAdapter!!.getRealPosition(adapterPosition)
  }

  companion object {
    private const val TAG = "DiscreteFragment"
  }
}
