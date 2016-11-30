package com.example.android.moviefragment.Data;

import android.provider.BaseColumns;

/**
 * Created by Jaren Lynch on 11/29/2016.
 */

public final class FavoritesContract {

    private FavoritesContract () {}

    public static final class FavoriteEntry implements BaseColumns {

        public final static String TABLE_NAME = "favorites";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_MOVIE_TITLE = "title";
        public final static String COLUMN_MOVIE_IMAGE = "image";
        public final static String COLUMN_MOVIE_PLOT = "plot";
        public final static String COLUMN_MOVIE_RATING = "rating";
        public final static String COLUMN_MOVIE_DATE = "date";
        public final static String COLUMN_MOVIE_DBID = "moviedb";





    }
}
