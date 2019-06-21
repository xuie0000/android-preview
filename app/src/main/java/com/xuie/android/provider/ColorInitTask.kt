package com.xuie.android.provider

import android.content.ContentValues
import android.os.AsyncTask
import androidx.core.content.ContextCompat
import com.xuie.android.App
import java.util.*

open class ColorInitTask : AsyncTask<Void, Int, Boolean>() {

  override fun doInBackground(vararg params: Void): Boolean? {
    // clean db
    App.context.contentResolver.delete(ColorContract.ColorEntry.CONTENT_URI, null, null)

    // add database
    val colors = intArrayOf(
        ContextCompat.getColor(App.context, android.R.color.holo_blue_dark),
        ContextCompat.getColor(App.context, android.R.color.holo_blue_bright),
        ContextCompat.getColor(App.context, android.R.color.holo_blue_light),
        ContextCompat.getColor(App.context, android.R.color.holo_green_light),
        ContextCompat.getColor(App.context, android.R.color.holo_orange_dark),
        ContextCompat.getColor(App.context, android.R.color.holo_purple),
        ContextCompat.getColor(App.context, android.R.color.holo_red_dark),
        ContextCompat.getColor(App.context, android.R.color.holo_red_light))

    var per = -1
    var cur: Int
    val r = Random()
    for (i in 0..59) {
      do {
        cur = r.nextInt(8)
      } while (cur == per)
      per = cur
      val contentValues = ContentValues()
      contentValues.put(ColorContract.ColorEntry.COLUMN_NAME, "element #$cur")
      contentValues.put(ColorContract.ColorEntry.COLUMN_COLOR, colors[cur])
      App.context.contentResolver.insert(ColorContract.ColorEntry.CONTENT_URI, contentValues)
    }
    return true
  }
}
