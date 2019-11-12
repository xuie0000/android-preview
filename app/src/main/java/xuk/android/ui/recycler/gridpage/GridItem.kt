package xuk.android.ui.recycler.gridpage

class GridItem(
    var name: String?,
    var other: String?,
    var source: Int,
    override var tag: String,
    override var spanSize: Int,
    var type: Int) : IGridItem {

  override val isShow: Boolean
    get() = true

  companion object {

    const val TYPE_SMALL = 1
    const val TYPE_NORMAL = 2
    const val TYPE_SPECIAL = 3
  }
}

/**
 * 网格首页数据需要实现的接口
 */
interface IGridItem {
  /**
   * 是否启用分割线
   * @return true
   */
  val isShow: Boolean

  /**
   * 分类标签
   */
  val tag: String

  /**
   * 权重
   */
  val spanSize: Int
}
