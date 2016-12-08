package com.example.android.popularmovies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {
    private String mUrl;
    public TrailerLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Trailer> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Trailer> trailers = TrailerQuery.fetchTrailerData(mUrl);
        return trailers;

    }
}