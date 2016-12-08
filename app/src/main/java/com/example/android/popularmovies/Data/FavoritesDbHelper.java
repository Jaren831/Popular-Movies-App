package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jaren Lynch on 11/29/2016.
 */

public class FavoritesDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = FavoritesDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "favorites.db";

    private static final int DATABASE_VERSION = 1;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesContract.FavoriteEntry.TABLE_NAME + " ("
                + FavoritesContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DBID + " TEXT NOT NULL,"
                + FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL,"
                + FavoritesContract.FavoriteEntry.COLUMN_MOVIE_RATING + " TEXT NOT NULL,"
                + FavoritesContract.FavoriteEntry.COLUMN_MOVIE_PLOT + " TEXT NOT NULL,"
                + FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DATE + " TEXT NOT NULL,"
                + FavoritesContract.FavoriteEntry.COLUMN_MOVIE_IMAGE + " BLOB NOT NULL);";

        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

