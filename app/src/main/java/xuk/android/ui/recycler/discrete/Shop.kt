package xuk.android.ui.recycler.discrete

import android.content.Context
import android.content.SharedPreferences
import xuk.android.App
import xuk.android.R

/**
 *
 * @author yarolegovich
 * @date 07.03.2017
 */
class Shop private constructor() {

  private val storage: SharedPreferences

  val data: List<Item>
    get() = listOf(
        Item(1, "Everyday Candle", "$12.00 USD", R.mipmap.one),
        Item(2, "Small Porcelain Bowl", "$50.00 USD", R.mipmap.two),
        Item(3, "Favourite Board", "$265.00 USD", R.mipmap.three),
        Item(4, "Earthenware Bowl", "$18.00 USD", R.mipmap.four),
        Item(5, "Porcelain Dessert Plate", "$36.00 USD", R.mipmap.five)
    )

  init {
    storage = App.context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE)
  }

  fun isRated(itemId: Int): Boolean {
    return storage.getBoolean(itemId.toString(), false)
  }

  fun setRated(itemId: Int, isRated: Boolean) {
    storage.edit().putBoolean(itemId.toString(), isRated).apply()
  }

  companion object {

    private const val STORAGE = "shop"

    fun get(): Shop {
      return Shop()
    }
  }
}
