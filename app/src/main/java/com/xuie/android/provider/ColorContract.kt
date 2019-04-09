package com.xuie.android.provider

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

/**
 * @author xuie
 * @date 2017/4/12 0012
 */

object ColorContract {
  /**
   * Content authority is a name for the entire content provider
   * similar to a domain and its website. This string is guaranteed to be unique.
   */
  val CONTENT_AUTHORITY = "com.xuie.android.data.content.provider"

  /**
   * Use the content authority to provide the base
   * of all URIs
   */
  private val BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")

  /**
   * Path for URI to a color
   */
  val PATH_COLOR = "color"

  /**
   * Class that defines the schema of the Color table.
   */
  class ColorEntry : BaseColumns {
    companion object {
      /**
       * Uri to access all movies
       */
      val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COLOR).build()

      /**
       * MIME types of Color queries
       */
      val CONTENT_TYPE =
          "vnd.android.cursor.dir/$CONTENT_URI/$PATH_COLOR"
      val CONTENT_ITEM_TYPE =
          "vnd.android.cursor.item/$CONTENT_URI/$PATH_COLOR"

      /**
       * Schema
       */
      val TABLE_NAME = "color_database"
      val COLUMN_NAME = "name"
      val COLUMN_COLOR = "color"

      /**
       * Builds a URI for an individual movie.
       */
      fun buildUri(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
      }
    }
  }

}
