package com.xuie.android.ui.palette

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mikepenz.iconics.IconicsDrawable
import com.xuie.android.R
import java.util.*

/**
 * @author Jie Xu
 */
class PaletteActivity : AppCompatActivity() {

  private var tabLayout: TabLayout? = null
  private var viewpager: ViewPager? = null
  private var toolbar: Toolbar? = null
  private var coordinator: CoordinatorLayout? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_palette)
    tabLayout = findViewById(R.id.tab_layout)
    viewpager = findViewById(R.id.viewpager)
    toolbar = findViewById(R.id.toolbar)
    coordinator = findViewById(R.id.coordinator)

    setSupportActionBar(toolbar)
    toolbar!!.setTitle(R.string.palette)
    toolbar!!.setTitleTextColor(Color.WHITE)
    toolbar!!.navigationIcon = IconicsDrawable(this, "gmi_chevron_left").sizeDp(24).color(Color.WHITE)
    toolbar!!.setNavigationOnClickListener { view -> finish() }

    val paletteViewPagerAdapter = PaletteViewPagerAdapter(supportFragmentManager)
    viewpager!!.adapter = paletteViewPagerAdapter
    tabLayout!!.setupWithViewPager(viewpager)
    changeTopBgColor(0)
    viewpager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

      override fun onPageSelected(position: Int) {
        changeTopBgColor(position)
      }

      override fun onPageScrollStateChanged(state: Int) {}
    })
  }

  private inner class PaletteViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    internal val pageCount = 5
    private val tabTitles = arrayOf("主页", "分享", "收藏", "关注", "微博")

    override fun getItem(position: Int): Fragment {
      return PaletteFragment.newInstance(position)
    }

    override fun getCount(): Int {
      return pageCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return tabTitles[position]
    }
  }


  /**
   * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
   */
  private fun changeTopBgColor(position: Int) {
    // 用来提取颜色的Bitmap
    val bitmap = BitmapFactory.decodeResource(resources, PaletteFragment.getBackgroundBitmapPosition(position))
    // Palette的部分
    val builder = Palette.from(bitmap)
    builder.generate { palette ->
      //获取到充满活力的这种色调
      val vibrant = palette!!.vibrantSwatch ?: return@generate
      //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
      tabLayout!!.setBackgroundColor(Objects.requireNonNull(vibrant).rgb)
      tabLayout!!.setSelectedTabIndicatorColor(colorBurn(vibrant.rgb))
      toolbar!!.setBackgroundColor(vibrant.rgb)
      coordinator!!.setBackgroundColor(vibrant.rgb)

      if (Build.VERSION.SDK_INT >= 21) {
        val window = window
        window.statusBarColor = colorBurn(vibrant.rgb)
        window.navigationBarColor = colorBurn(vibrant.rgb)
      }
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
    red = Math.floor(red * (1 - 0.1)).toInt()
    green = Math.floor(green * (1 - 0.1)).toInt()
    blue = Math.floor(blue * (1 - 0.1)).toInt()
    return Color.argb(alpha, red, green, blue)
  }
}
