package xuk.android.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import android.text.TextUtils

/**
 * @author Jie Xu
 * @date 2017/4/12 0012
 * https://developer.android.google.cn/guide/topics/providers/content-provider-creating.html?hl=zh-cn
 */
class ColorProvider : ContentProvider() {

  private lateinit var mOpenHelper: ColorDatabaseHelper

  override fun onCreate(): Boolean {
    mOpenHelper = ColorDatabaseHelper(context!!)
    return true
  }

  override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
    val cursor: Cursor
    val db = mOpenHelper.readableDatabase

    when (sUriMatcher.match(uri)) {
      COLOR -> cursor = db.query(
          ColorContract.ColorEntry.TABLE_NAME,
          projection,
          selection,
          selectionArgs, null, null,
          sortOrder
      )
      COLOR_ID -> {
        val id = ContentUris.parseId(uri)
        cursor = db.query(
            ColorContract.ColorEntry.TABLE_NAME,
            projection,
            BaseColumns._ID + " = ?",
            arrayOf(id.toString()), null, null,
            sortOrder
        )
      }
      else -> throw UnsupportedOperationException("Unknown uri: $uri")
    }

    // Set notification
    assert(context != null)
    cursor.setNotificationUri(context!!.contentResolver, uri)
    return cursor
  }

  override fun getType(uri: Uri): String? {
    return when (sUriMatcher.match(uri)) {
      COLOR -> ColorContract.ColorEntry.CONTENT_TYPE
      COLOR_ID -> ColorContract.ColorEntry.CONTENT_ITEM_TYPE
      else -> throw UnsupportedOperationException("Unknown uri: $uri")
    }
  }

  override fun insert(uri: Uri, values: ContentValues?): Uri? {
    val dbConnection = mOpenHelper.writableDatabase
    val returnUri: Uri
    val id: Long
    dbConnection.beginTransaction()
    when (buildUriMatcher().match(uri)) {
      COLOR, COLOR_ID -> {
        id = dbConnection.insert(ColorContract.ColorEntry.TABLE_NAME, null, values)
        if (id > 0) {
          returnUri = ColorContract.ColorEntry.buildUri(id)
        } else {
          throw android.database.SQLException("Failed to insert row into $uri")
        }
        context?.contentResolver?.notifyChange(returnUri, null)
        dbConnection.setTransactionSuccessful()
      }
      else -> throw android.database.SQLException("Unknown uri: $uri")
    }
    dbConnection.endTransaction()
    return returnUri
  }

  override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
    val dbConnection = mOpenHelper.writableDatabase
    var deleteCount = 0

    dbConnection.beginTransaction()

    when (buildUriMatcher().match(uri)) {
      COLOR -> {
        deleteCount = dbConnection.delete(ColorContract.ColorEntry.TABLE_NAME, selection, selectionArgs)
        dbConnection.setTransactionSuccessful()
      }
      COLOR_ID -> {
        deleteCount = dbConnection.delete(ColorContract.ColorEntry.TABLE_NAME, BaseColumns._ID + " = ?", arrayOf(uri.lastPathSegment))
        dbConnection.setTransactionSuccessful()
      }
    }
    dbConnection.endTransaction()
    if (deleteCount > 0) {
      context?.contentResolver?.notifyChange(uri, null)
    }
    return deleteCount
  }

  override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
    val dbConnection = mOpenHelper.writableDatabase
    var updateCount = 0

    dbConnection.beginTransaction()

    when (buildUriMatcher().match(uri)) {
      COLOR -> {
        updateCount = dbConnection.update(ColorContract.ColorEntry.TABLE_NAME, values, selection, selectionArgs)
        dbConnection.setTransactionSuccessful()
      }
      COLOR_ID -> {
        val personId = ContentUris.parseId(uri)
        updateCount = dbConnection.update(ColorContract.ColorEntry.TABLE_NAME, values,
            BaseColumns._ID + "=" + personId +
                if (TextUtils.isEmpty(selection)) "" else " AND ($selection)", selectionArgs)
        dbConnection.setTransactionSuccessful()
      }
    }
    dbConnection.endTransaction()
    if (updateCount > 0) {
      context?.contentResolver?.notifyChange(uri, null)
    }
    return updateCount
  }

  companion object {
    private const val COLOR = 0
    private const val COLOR_ID = 1
    private val sUriMatcher = buildUriMatcher()

    private fun buildUriMatcher(): UriMatcher {
      val content = ColorContract.CONTENT_AUTHORITY

      val matcher = UriMatcher(UriMatcher.NO_MATCH)
      matcher.addURI(content, ColorContract.PATH_COLOR, COLOR)
      matcher.addURI(content, ColorContract.PATH_COLOR + "/#", COLOR_ID)
      return matcher
    }
  }
}
