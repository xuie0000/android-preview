package xuk.android.ui.recycler.paging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

class CheeseViewModel(app: Application) : AndroidViewModel(app) {
  private val dao = CheeseDb.get(app).cheeseDao()

  val allCheeses = Pager(
      config = PagingConfig(
          pageSize = 30,
          prefetchDistance = 30,
          enablePlaceholders = true,
          initialLoadSize = 30 * 3,
          maxSize = 200
      ),
      remoteMediator = null,
      pagingSourceFactory = { dao.allCheesesByName() }
  ).flow

  fun insert(text: CharSequence) = ioThread {
    dao.insert(Cheese(id = 0, name = text.toString()))
  }

  fun remove(cheese: Cheese) = ioThread {
    dao.delete(cheese)
  }
}
