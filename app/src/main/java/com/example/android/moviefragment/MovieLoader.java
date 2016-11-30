package com.example.android.moviefragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.moviefragment.Data.FavoritesContract;
import com.example.android.moviefragment.Data.FavoritesDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaren Lynch on 9/25/2016.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private FavoritesDbHelper mDbHelper;
    private Cursor cursor;

    private String mUrl;
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {

        List<Movie> movies = new ArrayList<>();

        if (mUrl == null) {

            mDbHelper = new FavoritesDbHelper(getContext());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String[] columns = {
                    FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE,
                    FavoritesContract.FavoriteEntry.COLUMN_MOVIE_IMAGE,
                    FavoritesContract.FavoriteEntry.COLUMN_MOVIE_PLOT,
                    FavoritesContract.FavoriteEntry.COLUMN_MOVIE_RATING,
                    FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DATE,
                    FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DBID};
            cursor = db.query(FavoritesContract.FavoriteEntry.TABLE_NAME, columns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                int movieIDIndex = cursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DBID);
                String movieID = cursor.getString(movieIDIndex);

                int movieTitleIndex = cursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE);
                String movieTitle = cursor.getString(movieTitleIndex);

                int moviePlotIndex = cursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_PLOT);
                String moviePlot = cursor.getString(moviePlotIndex);

                int movieDateIndex = cursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DATE);
                String movieDate = cursor.getString(movieDateIndex);

                int movieRatingIndex = cursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_RATING);
                String movieRating = cursor.getString(movieRatingIndex);

                int movieImageIndex = cursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_IMAGE);
                byte[] movieImage = cursor.getBlob(movieImageIndex);
                Bitmap bmp = BitmapFactory.decodeByteArray(movieImage, 0, movieImage.length);

                movies.add(new Movie(movieTitle, bmp, moviePlot, movieRating,
                        movieDate, movieID));

            }
        } else {
             movies = MovieQuery.fetchMovieData(mUrl);
        }
        return movies;

    }
}
