package xuk.android.ui.palette

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
  private val tabTitles = arrayOf("主页", "分享", "收藏", "关注", "微博")

  override fun createFragment(position: Int): Fragment = PaletteFragment.newInstance(position)

  fun getPageTitle(position: Int): CharSequence = tabTitles[position]

  override fun getItemCount(): Int = tabTitles.size
}
