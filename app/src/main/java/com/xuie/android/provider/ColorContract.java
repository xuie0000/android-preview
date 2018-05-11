package com.xuie.android.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 *
 * @author xuie
 * @date 2017/4/12 0012
 */

public class ColorContract {
    /**
     * Content authority is a name for the entire content provider
     * similar to a domain and its website. This string is guaranteed to be unique.
     */
    public static final String CONTENT_AUTHORITY = "com.xuie.android.data.content.provider";

    /**
     * Use the content authority to provide the base
     * of all URIs
     */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path for URI to a color
     */
    public static final String PATH_COLOR = "color";

    /**
     * Class that defines the schema of the Color table.
     */
    public static final class ColorEntry implements BaseColumns {
        /**
         * Uri to access all movies
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COLOR).build();

        /**
         * MIME types of Color queries
         */
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_COLOR;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_COLOR;

        /**
         * Schema
         */
        public static final String TABLE_NAME = "color_database";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COLOR = "color";

        /**
         * Builds a URI for an individual movie.
         */
        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
