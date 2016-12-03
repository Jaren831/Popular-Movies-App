package com.example.android.moviefragment;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class Trailer {
    private String mYoutubeKey;

    private String mTrailerName;

    public Trailer(String youtubekey, String trailername) {
        mYoutubeKey = youtubekey;
        mTrailerName = trailername;
    }

    public String getYoutubeKey() {
        return mYoutubeKey;
    }

    public String getTrailerName() {
        return mTrailerName;
    }
}
