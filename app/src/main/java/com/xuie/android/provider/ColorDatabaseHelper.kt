package com.xuie.android.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * @author Jie Xu
 * @date 2017/4/12 0012
 */
const val DATABASE_NAME = "color_database.db"
const val DATABASE_VERSION = 1

class ColorDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

  override fun onCreate(db: SQLiteDatabase) {
    /*
 CREATE TABLE `color_database` (
 `_id`	INTEGER,
 `number`	INTEGER NOT NULL,
 `color`	INTEGER NOT NULL,
 PRIMARY KEY(_id)
 );
 */
    // Create table
    db.execSQL("CREATE TABLE " +
        ColorContract.ColorEntry.TABLE_NAME + " (\n" +
        BaseColumns._ID + "\tINTEGER,\n" +
        ColorContract.ColorEntry.COLUMN_NAME + "\tTEXT NOT NULL,\n" +
        ColorContract.ColorEntry.COLUMN_COLOR + "\tINTEGER NOT NULL,\n" +
        " PRIMARY KEY(_id)\n" +
        " );\n")
    // Insert sample data
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("DROP TABLE IF EXISTS " + ColorContract.ColorEntry.TABLE_NAME)
    onCreate(db)
  }
}
