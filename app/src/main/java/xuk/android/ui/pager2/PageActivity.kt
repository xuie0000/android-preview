package xuk.android.ui.pager2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_page.*
import xuk.android.R

class PageActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_page)

    val sectionsPagerAdapter = SectionsPagerAdapter(this)
    bar_title.text = getString(R.string.page2_title)
    view_pager.apply {
      adapter = sectionsPagerAdapter
      orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
    TabLayoutMediator(tabs, view_pager, true) { tab, position ->
      tab.text = sectionsPagerAdapter.getPageTitle(position)
    }.attach()
  }
}
