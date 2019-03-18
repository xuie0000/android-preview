package com.xuie.android.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author xuie
 * @date 2017/4/12 0012
 * https://developer.android.google.cn/guide/topics/providers/content-provider-creating.html?hl=zh-cn
 */
public class ColorProvider extends ContentProvider {
    private static final int COLOR = 0;
    private static final int COLOR_ID = 1;

    private ColorDatabaseHelper mOpenHelper;
    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        String content = ColorContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, ColorContract.PATH_COLOR, COLOR);
        matcher.addURI(content, ColorContract.PATH_COLOR + "/#", COLOR_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ColorDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case COLOR:
                cursor = db.query(
                        ColorContract.ColorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COLOR_ID:
                long id = ContentUris.parseId(uri);
                cursor = db.query(
                        ColorContract.ColorEntry.TABLE_NAME,
                        projection,
                        ColorContract.ColorEntry._ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set notification
        assert getContext() != null;
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case COLOR:
                return ColorContract.ColorEntry.CONTENT_TYPE;
            case COLOR_ID:
                return ColorContract.ColorEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase dbConnection = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long id;
        dbConnection.beginTransaction();
        switch (buildUriMatcher().match(uri)) {
            case COLOR:
            case COLOR_ID:
                id = dbConnection.insert(ColorContract.ColorEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ColorContract.ColorEntry.buildUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                getContext().getContentResolver().notifyChange(returnUri, null);
                dbConnection.setTransactionSuccessful();
                break;
            default:
                throw new android.database.SQLException("Unknown uri: " + uri);
        }
        dbConnection.endTransaction();
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase dbConnection = mOpenHelper.getWritableDatabase();
        int deleteCount = 0;

        dbConnection.beginTransaction();

        switch (buildUriMatcher().match(uri)) {
            case COLOR:
                deleteCount = dbConnection.delete(ColorContract.ColorEntry.TABLE_NAME, selection, selectionArgs);
                dbConnection.setTransactionSuccessful();
                break;
            case COLOR_ID:
                deleteCount = dbConnection.delete(ColorContract.ColorEntry.TABLE_NAME, ColorContract.ColorEntry._ID + " = ?", new String[]{uri.getLastPathSegment()});
                dbConnection.setTransactionSuccessful();
                break;
        }
        dbConnection.endTransaction();
        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase dbConnection = mOpenHelper.getWritableDatabase();
        int updateCount = 0;

        dbConnection.beginTransaction();

        switch (buildUriMatcher().match(uri)) {
            case COLOR:
                updateCount = dbConnection.update(ColorContract.ColorEntry.TABLE_NAME, values, selection, selectionArgs);
                dbConnection.setTransactionSuccessful();
                break;
            case COLOR_ID:
                final long personId = ContentUris.parseId(uri);
                updateCount = dbConnection.update(ColorContract.ColorEntry.TABLE_NAME, values,
                        ColorContract.ColorEntry._ID + "=" + personId +
                                (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);
                dbConnection.setTransactionSuccessful();
                break;
        }
        dbConnection.endTransaction();
        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }
}
