package xuk.android.ui.settings

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import xuk.android.R

class SettingsFragment : PreferenceFragmentCompat() {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.settings_preferences, rootKey)
    findPreference<Preference>("license")?.setOnPreferenceClickListener {
      findNavController().navigate(R.id.nav_licence)
      true
    }
  }
}