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

  /**
   * TODO 为什么插入一条数据之后Item数量对不上了？
   */
  fun insert(text: CharSequence) = viewModelScope.launch(Dispatchers.IO) {
    dao.insert(Cheese(id = 0, name = text.toString()))
  }

  fun remove(cheese: Cheese) = ioThread {
    dao.delete(cheese)
  }
}
