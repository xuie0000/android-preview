package xuk.android.ui.pager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import xuk.android.R

class PageActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_page)

    val sectionsPagerAdapter = SectionsPagerAdapter(this)
    val viewPager = findViewById<ViewPager2>(R.id.view_pager).apply {
      adapter = sectionsPagerAdapter
      orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
    val tabs: TabLayout = findViewById(R.id.tabs)
    TabLayoutMediator(tabs, viewPager, true) { tab, position ->
      tab.text = sectionsPagerAdapter.getPageTitle(position)
    }.attach()
  }
}
