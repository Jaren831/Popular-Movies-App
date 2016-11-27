package com.example.android.moviefragment;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class Movie implements Parcelable {
    private String mTitle;

    private Bitmap mImage;

    private String mPlot;

    private int mRating;

    private String mDate;

    private String mTrailer;

    public Movie(String title, Bitmap image, String plot, int rating, String date, String trailer) {
        mTitle = title;
        mImage = image;
        mPlot = plot;
        mRating = rating;
        mDate = date;
        mTrailer = trailer;
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

    public String getTrailer() {
        return mTrailer;
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mImage = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        mPlot = in.readString();
        mRating = in.readInt();
        mDate = in.readString();
        mTrailer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeValue(mImage);
        dest.writeString(mPlot);
        dest.writeInt(mRating);
        dest.writeString(mDate);
        dest.writeString(mTrailer);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}
