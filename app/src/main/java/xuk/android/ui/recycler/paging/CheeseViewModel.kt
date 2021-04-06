package xuk.android.ui.recycler.paging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheeseViewModel(app: Application) : AndroidViewModel(app) {
  private val dao = CheeseDb.get(app).cheeseDao()

  val allCheeses = Pager(
    config = PagingConfig(
      pageSize = 20,
      enablePlaceholders = false,
      initialLoadSize = 20 * 3
    ),
    pagingSourceFactory = { dao.allCheesesByName() }
  ).flow


  fun insert(text: CharSequence) = ioThread {
    dao.insert(Cheese(id = 0, name = text.toString()))
  }

  fun remove(cheese: Cheese) = ioThread {
    dao.delete(cheese)
  }
}
