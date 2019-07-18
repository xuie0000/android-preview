package xuk.android.ui.recycler.discrete

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View

import androidx.appcompat.widget.PopupMenu

import xuk.android.App
import xuk.android.R
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter

import java.lang.ref.WeakReference

/**
 * @author yarolegovich
 * @date 08.03.2017
 */

class DiscreteScrollViewOptions private constructor(context: Context) {

  private val keyTransitionTime = context.getString(R.string.pref_key_transition_time)

  private class TransitionTimeChangeListener(scrollView: DiscreteScrollView) : SharedPreferences.OnSharedPreferenceChangeListener {

    private val scrollView: WeakReference<DiscreteScrollView> = WeakReference(scrollView)

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
      if (key == instance!!.keyTransitionTime) {
        val scrollView = this.scrollView.get()
        if (scrollView != null) {
          scrollView.setItemTransitionTimeMillis(sharedPreferences.getInt(key, 150))
        } else {
          sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
      }
    }
  }

  companion object {

    private var instance: DiscreteScrollViewOptions? = null

    fun init(context: Context) {
      instance = DiscreteScrollViewOptions(context)
    }

    fun smoothScrollToUserSelectedPosition(scrollView: DiscreteScrollView, anchor: View) {
      val popupMenu = PopupMenu(scrollView.context, anchor)
      val menu = popupMenu.menu
      val adapter = scrollView.adapter
      val itemCount = (adapter as? InfiniteScrollAdapter<*>)?.realItemCount ?: adapter!!.itemCount
      for (i in 0 until itemCount) {
        menu.add((i + 1).toString())
      }
      popupMenu.setOnMenuItemClickListener { item ->
        var destination = Integer.parseInt(item.title.toString()) - 1
        if (adapter is InfiniteScrollAdapter<*>) {
          destination = adapter.getClosestPosition(destination)
        }
        scrollView.smoothScrollToPosition(destination)
        true
      }
      popupMenu.show()
    }

    val transitionTime: Int
      get() = defaultPrefs().getInt(instance!!.keyTransitionTime, 150)

    private fun defaultPrefs(): SharedPreferences {

      return PreferenceManager.getDefaultSharedPreferences(App.context)
    }
  }
}
