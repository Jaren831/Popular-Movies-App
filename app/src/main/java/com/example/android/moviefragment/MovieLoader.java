package com.example.android.moviefragment;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Jaren Lynch on 9/25/2016.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    /** Tag for log messages */
    private static final String LOG_TAG = MovieLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: Main Activity onStartLoading() called");

        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        Log.i(LOG_TAG, "TEST: Main Activity loadInBackground() called");

        if (mUrl == null) {
            return null;
        }
        List<Movie> earthquakes = MovieQuery.fetchMovieData(mUrl);
        return earthquakes;

    }
}
