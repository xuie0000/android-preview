package xuk.android.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * @author xuie
 * @date 17-1-18
 */
object PreferenceUtils {
  private var mSharedPreferences: SharedPreferences? = null
  private var mEditor: SharedPreferences.Editor? = null

  fun init(context: Context) {
    if (mSharedPreferences == null) {
      mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    }
  }

  fun setString(key: String, value: String) {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    mEditor!!.putString(key, value)
    mEditor!!.apply()
  }

  fun getString(key: String, defaultValue: String): String? {
    checkNOTNull()
    return mSharedPreferences!!.getString(key, defaultValue)
  }

  fun setInt(key: String, value: Int) {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    mEditor!!.putInt(key, value)
    mEditor!!.apply()
  }

  fun getInt(key: String, defaultValue: Int): Int {
    checkNOTNull()
    return mSharedPreferences!!.getInt(key, defaultValue)
  }

  fun setLong(key: String, value: Long) {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    mEditor!!.putLong(key, value)
    mEditor!!.apply()
  }

  fun getLong(key: String, defaultValue: Long): Long {
    checkNOTNull()
    return mSharedPreferences!!.getLong(key, defaultValue)
  }

  fun setBoolean(key: String, value: Boolean) {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    mEditor!!.putBoolean(key, value)
    mEditor!!.apply()
  }

  fun getBoolean(key: String, defaultValue: Boolean): Boolean {
    checkNOTNull()
    return mSharedPreferences!!.getBoolean(key, defaultValue)
  }

  fun removeKey(key: String) {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    mEditor!!.remove(key)
    mEditor!!.apply()
  }

  fun removeAll() {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    mEditor!!.clear()
    mEditor!!.apply()
  }

  fun setPreference(key: String, `object`: Any) {
    checkNOTNull()
    mEditor = mSharedPreferences!!.edit()
    if (`object` is String) {
      mEditor!!.putString(key, `object`)
    } else if (`object` is Int) {
      mEditor!!.putInt(key, `object`)
    } else if (`object` is Boolean) {
      mEditor!!.putBoolean(key, `object`)
    } else if (`object` is Float) {
      mEditor!!.putFloat(key, `object`)
    } else if (`object` is Long) {
      mEditor!!.putLong(key, `object`)
    } else {
      mEditor!!.putString(key, `object`.toString())
    }
    mEditor!!.apply()
  }

  private fun checkNOTNull() {
    if (mSharedPreferences == null) {
      throw RuntimeException("SharedPreferences unInit")
    }
  }
}
