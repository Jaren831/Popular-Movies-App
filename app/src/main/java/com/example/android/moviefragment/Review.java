package com.example.android.moviefragment;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class Review {
    private String mAuthor;

    private String mReview;

    public Review(String author,String review) {
        mAuthor = author;
        mReview = review;
    }

    public String getAuthor() {
        return mAuthor;
    }
    public String getReview() {
        return mReview;
    }
}
