package com.xuie.android.ui.recycler.normal

import android.content.Context
import android.database.Cursor
import android.database.DataSetObserver

import androidx.recyclerview.widget.RecyclerView

/**
 * https://github.com/AbhirojPanwar/Wallser
 *
 * @param <VH> [RecyclerView.ViewHolder]
 * @author xuie
</VH> */
abstract class CursorRecyclerViewAdapter<VH : RecyclerView.ViewHolder>(private val mContext: Context, cursor: Cursor?) : RecyclerView.Adapter<VH>() {

  var cursor: Cursor? = null
    private set

  private var mDataValid: Boolean = false

  private var mRowIdColumn: Int = 0

  private val mDataSetObserver: DataSetObserver?

  init {
    this.cursor = cursor
    mDataValid = cursor != null
    mRowIdColumn = if (mDataValid) this.cursor!!.getColumnIndex("_id") else -1
    mDataSetObserver = NotifyingDataSetObserver()
    if (this.cursor != null) {
      this.cursor!!.registerDataSetObserver(mDataSetObserver)
    }
  }

  override fun getItemCount(): Int {
    return if (mDataValid && cursor != null) {
      cursor!!.count
    } else 0
  }

  override fun getItemId(position: Int): Long {
    return if (mDataValid && cursor != null && cursor!!.moveToPosition(position)) {
      cursor!!.getLong(mRowIdColumn)
    } else 0
  }

  override fun setHasStableIds(hasStableIds: Boolean) {
    super.setHasStableIds(true)
  }

  abstract fun onBindViewHolder(viewHolder: VH, cursor: Cursor)

  override fun onBindViewHolder(viewHolder: VH, position: Int) {
    if (!mDataValid) {
      throw IllegalStateException("this should only be called when the cursor is valid")
    }
    if (!cursor!!.moveToPosition(position)) {
      throw IllegalStateException("couldn't move cursor to position $position")
    }
    onBindViewHolder(viewHolder, cursor)
  }

  /**
   * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
   * closed.
   */
  fun changeCursor(cursor: Cursor) {
    val old = swapCursor(cursor)
    old?.close()
  }

  /**
   * Swap in a new Cursor, returning the old Cursor.  Unlike
   * [.changeCursor], the returned old Cursor is *not*
   * closed.
   */
  fun swapCursor(newCursor: Cursor): Cursor? {
    if (newCursor === cursor) {
      return null
    }
    val oldCursor = cursor
    if (oldCursor != null && mDataSetObserver != null) {
      oldCursor.unregisterDataSetObserver(mDataSetObserver)
    }
    cursor = newCursor
    if (cursor != null) {
      if (mDataSetObserver != null) {
        cursor!!.registerDataSetObserver(mDataSetObserver)
      }
      mRowIdColumn = newCursor.getColumnIndexOrThrow("_id")
      mDataValid = true
      notifyDataSetChanged()
    } else {
      mRowIdColumn = -1
      mDataValid = false
      notifyDataSetChanged()
      //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
    }
    return oldCursor
  }

  private inner class NotifyingDataSetObserver : DataSetObserver() {
    override fun onChanged() {
      super.onChanged()
      mDataValid = true
      notifyDataSetChanged()
    }

    override fun onInvalidated() {
      super.onInvalidated()
      mDataValid = false
      notifyDataSetChanged()
      //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
    }
  }
}