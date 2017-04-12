package com.xuie.android.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xuie on 2017/4/12 0012.
 */

public class ColorDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "color_database.db";
    private static final int DATABASE_VERSION = 1;

    public ColorDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                ColorContract.ColorEntry._ID + "\tINTEGER,\n" +
                ColorContract.ColorEntry.COLUMN_NAME + "\tTEXT NOT NULL,\n" +
                ColorContract.ColorEntry.COLUMN_COLOR + "\tINTEGER NOT NULL,\n" +
                " PRIMARY KEY(_id)\n" +
                " );\n");
        // Insert sample data
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ColorContract.ColorEntry.TABLE_NAME);
        onCreate(db);
    }
}
