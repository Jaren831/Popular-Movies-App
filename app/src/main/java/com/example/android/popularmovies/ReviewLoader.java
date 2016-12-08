package com.example.android.popularmovies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {
    private String mUrl;
    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Review> reviews = ReviewQuery.fetchReviewData(mUrl);
        return reviews;

    }
}
