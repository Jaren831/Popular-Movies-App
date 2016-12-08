package com.example.android.popularmovies;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class Review {
    private String mAuthor;

    private String mReview;

    private String mUrl;

    public Review(String author,String review, String url) {
        mAuthor = author;
        mReview = review;
        mUrl = url;
    }

    public String getAuthor() {
        return mAuthor;
    }
    public String getReview() {
        return mReview;
    }

    public String getUrl() {
        return mUrl;
    }
}
