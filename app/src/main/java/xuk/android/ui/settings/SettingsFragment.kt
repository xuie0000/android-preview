package xuk.android.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import timber.log.Timber
import xuk.android.R

class SettingsFragment : PreferenceFragmentCompat() {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.settings_preferences, rootKey)

    findPreference<ListPreference>(getString(R.string.key_theme))?.setOnPreferenceChangeListener { _, newValue ->
      Timber.d("newValue:$newValue")
      val theme = when (newValue) {
        getString(R.string.follow_system) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        getString(R.string.auto_battery) -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        getString(R.string.night_no) -> AppCompatDelegate.MODE_NIGHT_NO
        getString(R.string.night_yes) -> AppCompatDelegate.MODE_NIGHT_YES
        else -> throw IllegalStateException("theme value error!")
      }
      AppCompatDelegate.setDefaultNightMode(theme)
      true
    }

    findPreference<Preference>("license")?.setOnPreferenceClickListener {
      findNavController().navigate(R.id.nav_licence)
      true
    }
  }
}