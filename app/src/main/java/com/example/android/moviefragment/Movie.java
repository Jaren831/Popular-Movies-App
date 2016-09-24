package com.example.android.moviefragment;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class Movie {
    private String mTitle;

    private byte[] mImage;

    private String mPlot;

    private int mRating;

    private String mDate;

    public Movie(String title, byte[] image, String plot, int rating, String date) {
        mTitle = title;
        mImage = image;
        mPlot = plot;
        mRating = rating;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public byte[] getImage() {
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
