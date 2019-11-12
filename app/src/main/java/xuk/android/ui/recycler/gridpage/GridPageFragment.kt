package xuk.android.ui.recycler.gridpage

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_gird_page.*
import xuk.android.R
import java.util.*

/**
 * @author Jie Xu
 */
class GridPageFragment : Fragment(R.layout.fragment_gird_page) {

  private val values: MutableList<GridItem> by lazy { initData() }
  private val itemDecoration: GridItemDecoration by lazy {
    GridItemDecoration.Builder(context!!, values, 3)
        .setTitleTextColor(Color.parseColor("#4e5864"))
        //.setTitleBgColor(Color.parseColor("#008577"))
        .setTitleFontSize(22)
        .setTitleHeight(52)
        .build()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initWidget()
  }

  private fun initWidget() {

    val grid = GridPageAdapter()

    recyclerView.apply {
      layoutManager = GridLayoutManager(context, 3).apply {
        spanSizeLookup = SpecialSpanSizeLookup()
      }
      adapter = grid
    }

    grid.setOnItemClickListener { _, position, _ ->
      Toast.makeText(context, "Click Position $position", Toast.LENGTH_SHORT).show()
    }
    grid.submitList(values)

    recyclerView.addItemDecoration(itemDecoration)
  }

  private fun initData(): MutableList<GridItem> {
    val values = ArrayList<GridItem>()
    values.add(GridItem("我很忙", "", R.drawable.grid_head_1, "最近常听", 1, GridItem.TYPE_SMALL))
    values.add(GridItem("治愈：有些歌比闺蜜更懂你", "", R.drawable.grid_head_2, "最近常听", 1, GridItem.TYPE_SMALL))
    values.add(GridItem("「华语」90后的青春纪念手册", "", R.drawable.grid_head_3, "最近常听", 1, GridItem.TYPE_SMALL))

    values.add(GridItem("流行创作女神你霉，泰勒斯威夫特的创作历程", "", R.drawable.grid_special_2, "更多为你推荐", 3, GridItem.TYPE_SPECIAL))
    values.add(GridItem("行走的CD写给别人的歌", "给「跟我走吧」几分，试试这些", R.drawable.grid_normal_1, "更多为你推荐", 3, GridItem.TYPE_NORMAL))
    values.add(GridItem("爱情里的酸甜苦辣，让人捉摸不透", "听完「靠近一点点」，他们等你翻牌", R.drawable.grid_normal_2, "更多为你推荐", 3, GridItem.TYPE_NORMAL))
    values.add(GridItem("关于喜欢你这件事，我都写在了歌里", "「好想你」听罢，听它们吧", R.drawable.grid_normal_3, "更多为你推荐", 3, GridItem.TYPE_NORMAL))
    values.add(GridItem("周杰伦暖心混剪，短短几分钟是多少人的青春", "", R.drawable.grid_special_1, "更多为你推荐", 3, GridItem.TYPE_SPECIAL))
    values.add(GridItem("我好想和你一起听雨滴", "给「发如雪」几分，那这些呢", R.drawable.grid_normal_4, "更多为你推荐", 3, GridItem.TYPE_NORMAL))
    values.add(GridItem("油管周杰伦热门单曲Top20", "「周杰伦」的这些哥，你听了吗", R.drawable.grid_normal_5, "更多为你推荐", 3, GridItem.TYPE_NORMAL))

    return values
  }

  inner class SpecialSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int = values[position].spanSize
  }

}
