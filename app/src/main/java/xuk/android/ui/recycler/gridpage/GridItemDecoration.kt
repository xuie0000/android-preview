package xuk.android.ui.recycler.gridpage

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xuk.android.util.dp2px
import xuk.android.util.sp2px
import java.util.*

/**
 * 适用于GridLayoutManager的分割线
 */
class GridItemDecoration
private constructor(config: Config, private val mContext: Context)
  : RecyclerView.ItemDecoration() {

  // 记录上次偏移位置 防止一行多个数据的时候视图偏移
  private val offsetPositions = ArrayList<Int>()
  // 显示数据
  private var gridItems: MutableList<out IGridItem>? = null
  // 画笔
  private val mTitlePaint: Paint = Paint()
  // 存放文字
  private val mRect: Rect
  // 颜色
  private var mTitleBgColor: Int = 0
  private var mTitleColor: Int = 0
  private var mTitleHeight: Int = 0
  private val mTitleFontSize: Int
  private var isDrawTitleBg = false

  // 总的SpanSize
  private var totalSpanSize: Int = 0
  private var mCurrentSpanSize: Int = 0

  init {
    mTitlePaint.isAntiAlias = true
    mTitlePaint.isDither = true
    mTitleFontSize = sp2px(config.titleFontSize.toFloat())
    mRect = Rect()
    init(config)
  }

  private fun init(config: Config) {
    this.gridItems = config.gridItems
    this.totalSpanSize = config.totalSpanSize
    this.mTitleBgColor = config.titleBgColor
    this.mTitleColor = config.titleTextColor
    this.mTitleHeight = mContext.dp2px(config.titleHeight.toFloat())
    this.isDrawTitleBg = config.isDrawTitleBg
  }

  /**
   * 更新部分数据
   */
  fun addItems(items: List<*>) {
//    this.gridItems!!.addAll(items)
  }

  /**
   * 对于当前位置前的数据需要更新，不然，对于文字下方的多列的首行视图会发生偏移
   *
   * @param items 更换的数据
   * @param pos   当前可见视图在数据中的位置
   */
  @JvmOverloads
  fun replace(items: MutableList<out IGridItem>?, pos: Int = 0) {
    this.offsetPositions.clear()
    if (items == null || items.size == 0) {
      remove()
      return
    }
    if (pos >= items.size)
      throw UnsupportedOperationException()

    this.gridItems = items
    var currentSpanSize = gridItems!![0].spanSize
    for (i in 1 until pos) {
      val item = items[i]
      val lastItem = items[i - 1]
      if (item.tag != lastItem.tag) {
        currentSpanSize = item.spanSize
        offsetPositions.add(i)
        continue
      }

      currentSpanSize += item.spanSize
      if (currentSpanSize <= totalSpanSize) {
        offsetPositions.add(i)
      }
    }
  }


  fun remove() {
    this.gridItems!!.clear()
    this.offsetPositions.clear()
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(c, parent, state)

    val paddingLeft = parent.paddingLeft
    val paddingRight = parent.paddingRight
    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      val params = child.layoutParams as RecyclerView.LayoutParams
      val pos = params.viewLayoutPosition
      val item = gridItems!![pos]
      if (item == null || !item.isShow)
        continue

      if (i == 0) {
        drawTitle(c, paddingLeft, paddingRight, child, child.layoutParams as RecyclerView.LayoutParams, pos, parent)
      } else {
        val lastItem = gridItems!![pos - 1]
        if (lastItem != null && item.tag != lastItem.tag) {
          drawTitle(c, paddingLeft, paddingRight, child,
              child.layoutParams as RecyclerView.LayoutParams, pos, parent)
        }
      }
    }

  }

  /**
   * 绘制标题
   *
   * @param canvas 画布
   * @param pl     左边距
   * @param pr     右边距
   * @param child  子View
   * @param params RecyclerView.LayoutParams
   * @param pos    位置
   */
  private fun drawTitle(canvas: Canvas, pl: Int, pr: Int, child: View, params: RecyclerView.LayoutParams, pos: Int, parent: RecyclerView) {
    val prentLayoutParams = parent.layoutParams
    if (isDrawTitleBg) {
      mTitlePaint.color = mTitleBgColor
      canvas.drawRect(pl.toFloat(), (child.top - params.topMargin - mTitleHeight).toFloat(), (parent.right - parent.paddingEnd).toFloat(), (child.top - params.topMargin).toFloat(), mTitlePaint)
    }

    val item = gridItems!![pos]
    val content = item.tag
    if (TextUtils.isEmpty(content))
      return

    mTitlePaint.color = mTitleColor
    mTitlePaint.textSize = mTitleFontSize.toFloat()
    mTitlePaint.typeface = Typeface.DEFAULT_BOLD
    mTitlePaint.getTextBounds(content, 0, content.length, mRect)
    val x = mContext.dp2px(20f).toFloat()
    val y = (child.top - params.topMargin - (mTitleHeight - mRect.height()) / 2).toFloat()
    canvas.drawText(content, x, y, mTitlePaint)
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    super.getItemOffsets(outRect, view, parent, state)


    val position = parent.getChildAdapterPosition(view)
    val item = gridItems!![position]
    if (item == null || !item.isShow)
      return
    if (position == 0) {
      outRect.set(0, mTitleHeight, 0, 0)
      mCurrentSpanSize = item.spanSize
    } else {
      if (offsetPositions.isNotEmpty() && offsetPositions.contains(position)) {
        outRect.set(0, mTitleHeight, 0, 0)
        return
      }

      if (!TextUtils.isEmpty(item.tag) && item.tag != gridItems!![position - 1].tag) {
        mCurrentSpanSize = item.spanSize
      } else
        mCurrentSpanSize += item.spanSize

      if (mCurrentSpanSize <= totalSpanSize) {
        outRect.set(0, mTitleHeight, 0, 0)
        offsetPositions.add(position)
      }
    }
  }

  internal class Config {
    // 数据
    var gridItems: MutableList<out IGridItem>? = null
    // 总的SpanSize 来自GridLayoutManager
    var totalSpanSize: Int = 0
    // 颜色 默认颜色
    var isDrawTitleBg = false
    var titleBgColor: Int = 0
    var titleTextColor = Color.parseColor("#4e5864")
    // 高度 40dp
    var titleHeight = 40
    // 文本大小 24sp
    var titleFontSize = 40
  }

  class Builder(private val context: Context, gridItems: MutableList<GridItem>?, totalSpanSize: Int) {
    private val config: Config = Config()

    init {
      config.gridItems = gridItems
      config.totalSpanSize = totalSpanSize
    }

    fun setTitleBgColor(titleBgColor: Int): Builder {
      config.titleBgColor = titleBgColor
      config.isDrawTitleBg = true
      return this
    }

    fun setTitleTextColor(titleTextColor: Int): Builder {
      config.titleTextColor = titleTextColor
      return this
    }

    /**
     * 设置高度
     *
     * @param titleHeight 高度 单位为Dp
     */
    fun setTitleHeight(titleHeight: Int): Builder {
      config.titleHeight = titleHeight
      return this
    }

    /**
     * 设置文本大小
     *
     * @param fontSize 文本带下 单位为Sp
     */
    fun setTitleFontSize(fontSize: Int): Builder {
      config.titleFontSize = fontSize
      return this
    }

    fun build(): GridItemDecoration {
      return GridItemDecoration(config, context)
    }
  }

}
