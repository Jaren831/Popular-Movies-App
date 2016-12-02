package com.example.android.moviefragment;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Jaren Lynch on 9/25/2016.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;
    private Boolean mFavorites;
    private Context mContext;
    public MovieLoader(Context context, String url, Boolean favorites) {
        super(context);
        mUrl = url;
        mFavorites = favorites;
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Movie> movies = MovieQuery.fetchMovieData(mUrl, mFavorites, mContext);
        return movies;

    }
}
