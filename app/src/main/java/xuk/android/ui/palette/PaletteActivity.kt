package xuk.android.ui.palette

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import xuk.android.R
import xuk.android.databinding.ActivityPaletteBinding
import java.util.*
import kotlin.math.floor

/**
 * 调色板Palette && ViewPager2 && Toolbar
 */
class PaletteActivity : AppCompatActivity() {

  private lateinit var binding: ActivityPaletteBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ActivityPaletteBinding.inflate(layoutInflater).apply {
      binding = this
      setContentView(root)

      setSupportActionBar(toolbar)
      supportActionBar?.apply {
        setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px)
        setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.palette)
      }

      val sectionsPagerAdapter = SectionsPagerAdapter(this@PaletteActivity)
      viewpager.apply {
        adapter = sectionsPagerAdapter
        orientation = ViewPager2.ORIENTATION_HORIZONTAL
      }

      TabLayoutMediator(tabLayout, viewpager, true) { tab, position ->
        tab.text = sectionsPagerAdapter.getPageTitle(position)
      }.attach()
      viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          changeTopBgColor(position)
        }
      })

      changeTopBgColor(0)
    }


  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
      return true
    }
    return super.onOptionsItemSelected(item)
  }


  /**
   * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
   */
  private fun changeTopBgColor(position: Int) {
    // 用来提取颜色的Bitmap
    val bitmap =
      BitmapFactory.decodeResource(resources, PaletteFragment.getBackgroundBitmapPosition(position))
    // Palette的部分
    val builder = Palette.from(bitmap)
    builder.generate { palette ->
      //获取到充满活力的这种色调
      val vibrant = palette?.vibrantSwatch ?: return@generate
      //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
      binding.tabLayout.setBackgroundColor(Objects.requireNonNull(vibrant).rgb)
      binding.tabLayout.setSelectedTabIndicatorColor(colorBurn(vibrant.rgb))
      binding.toolbar.setBackgroundColor(vibrant.rgb)
      binding.coordinator.setBackgroundColor(vibrant.rgb)

      window.statusBarColor = colorBurn(vibrant.rgb)
      window.navigationBarColor = colorBurn(vibrant.rgb)
    }
  }

  /**
   * 颜色加深处理
   *
   * @param rgbValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
   * Android中我们一般使用它的16进制，
   * 例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
   * red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
   * 所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
   */
  private fun colorBurn(rgbValues: Int): Int {
    val alpha = rgbValues shr 24
    var red = rgbValues shr 16 and 0xFF
    var green = rgbValues shr 8 and 0xFF
    var blue = rgbValues and 0xFF
    red = floor(red * (1 - 0.1)).toInt()
    green = floor(green * (1 - 0.1)).toInt()
    blue = floor(blue * (1 - 0.1)).toInt()
    return Color.argb(alpha, red, green, blue)
  }
}
