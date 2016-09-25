package com.example.android.moviefragment;

import android.graphics.Bitmap;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class Movie {
    private String mTitle;

    private Bitmap mImage;

    private String mPlot;

    private int mRating;

    private String mDate;

    public Movie(String title, Bitmap image, String plot, int rating, String date) {
        mTitle = title;
        mImage = image;
        mPlot = plot;
        mRating = rating;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public String getPlot() {
        return mPlot;
    }

    public int getRating() {
        return mRating;
    }

    public String getDate() {
        return mDate;
    }
}
