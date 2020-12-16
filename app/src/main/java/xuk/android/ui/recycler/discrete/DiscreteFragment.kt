package xuk.android.ui.recycler.discrete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.Orientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_discrete.*
import xuk.android.R

class DiscreteFragment : Fragment(R.layout.fragment_discrete),
    DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder> {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val shop = Shop.get()
    val data = shop.data
    item_picker.apply {
      setOrientation(Orientation.HORIZONTAL)
      addOnItemChangedListener(this@DiscreteFragment)
      adapter = InfiniteScrollAdapter.wrap(ShopAdapter(data))

      setSlideOnFling(true)
      setItemTransitionTimeMillis(DiscreteScrollViewOptions.transitionTime)
      setItemTransformer(ScaleTransformer.Builder()
          .setMinScale(0.8f)
          .build())
    }
  }

  override fun onCurrentItemChanged(viewHolder: RecyclerView.ViewHolder?, adapterPosition: Int) {

  }
}
