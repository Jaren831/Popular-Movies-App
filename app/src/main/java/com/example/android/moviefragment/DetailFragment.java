package com.example.android.moviefragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviefragment.Data.FavoritesContract;
import com.example.android.moviefragment.Data.FavoritesDbHelper;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.android.moviefragment.R.id.detail_image;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class DetailFragment extends Fragment {

    public static final String REVIEW_URL = "http://api.themoviedb.org/3/movie/83542/reviews";
    public static final String TRAILER_URL = "http://api.themoviedb.org/3/movie/157336/videos";

    public static final String API_KEY = "42e95964a6932d7d9b7d25f1c0c70c01";

    private static final int LOADER_REVIEW = 0;
    private static final int LOADER_TRAILER = 1;

    SQLiteDatabase db;
    FavoritesDbHelper mDbhelper;
    Bitmap bmp;
    ImageView movieImage;

    String movieId;
    String movieTitle;
    String moviePlot;
    String movieRating;
    String movieDate;

    ListView trailerList;
    String trailerName;
    TrailerAdapter trailerAdapter;

    ListView reviewList;
    String authorName;
    String reviewText;
    ReviewAdapter reviewAdapter;

    TextView emptyReview;
    TextView emptyTrailer;


    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        Bundle bundle = this.getArguments();
        final Movie currentMovie = bundle.getParcelable("selectedMovie");

        movieImage = (ImageView) rootView.findViewById(detail_image);
        if (currentMovie.getImage() != null) {
            movieImage.setImageBitmap(currentMovie.getImage());
        } else {
            movieImage.setImageResource(R.drawable.ic_help_outline_black_24dp);
        }

        TextView detailText = (TextView) rootView.findViewById(R.id.detail_title);
        detailText.setText(currentMovie.getTitle());

        TextView detailPlot = (TextView) rootView.findViewById(R.id.detail_plot);
        detailPlot.setSelected(true);
        detailPlot.setText(currentMovie.getPlot());

        TextView detailRating = (TextView) rootView.findViewById(R.id.detail_rating);
        detailRating.setText(currentMovie.getRating() + " / 10");

        TextView detailDate = (TextView) rootView.findViewById(R.id.detail_date);
        detailDate.setText(currentMovie.getDate());

        movieId = currentMovie.getID();
        movieTitle = currentMovie.getTitle();
        moviePlot = currentMovie.getPlot();
        movieRating = currentMovie.getRating();
        movieDate = currentMovie.getDate();

        CheckBox favoriteCheck = (CheckBox) rootView.findViewById(R.id.favorite_check);
        if (CheckIfFavorite(movieId)) {
            favoriteCheck.setChecked(true);
        } else {
            favoriteCheck.setChecked(false);
        }

        favoriteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDbhelper = new FavoritesDbHelper(getActivity());

                db = mDbhelper.getWritableDatabase();

                if (isChecked) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bmp =((BitmapDrawable)movieImage.getDrawable()).getBitmap();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bArray = byteArrayOutputStream.toByteArray();

                    ContentValues values = new ContentValues();
                    values.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DBID, movieId);
                    values.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_IMAGE, bArray);
                    values.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE, movieTitle);
                    values.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_PLOT, moviePlot);
                    values.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_RATING, movieRating);
                    values.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DATE, movieDate);

                    long newRowId = db.insert(FavoritesContract.FavoriteEntry.TABLE_NAME, null, values);
                } else {
                    db.delete(FavoritesContract.FavoriteEntry.TABLE_NAME, " moviedb" + "=" + movieId, null);
                }
                db.close();
            }
        });


        getLoaderManager().restartLoader(LOADER_REVIEW, null, new LoaderReviewCallbacks());
        getLoaderManager().restartLoader(LOADER_TRAILER, null, new LoaderTrailerCallbacks());

        reviewList = (ListView) rootView.findViewById(R.id.review_list);
        emptyReview = (TextView) rootView.findViewById(R.id.empty_review);
        reviewAdapter = new ReviewAdapter(getContext());
        reviewList.setAdapter(reviewAdapter);

        trailerList = (ListView) rootView.findViewById(R.id.trailer_list);
        emptyTrailer = (TextView) rootView.findViewById(R.id.empty_trailer);
        trailerAdapter = new TrailerAdapter(getContext());
        trailerList.setAdapter(reviewAdapter);

        return rootView;
    }

    public boolean CheckIfFavorite(String movieId) {
        mDbhelper = new FavoritesDbHelper(getActivity());
        db = mDbhelper.getReadableDatabase();
        String filter = "moviedb=" + movieId;
        String[] columns = {
                FavoritesContract.FavoriteEntry.COLUMN_MOVIE_DBID
        };
        Cursor cursor = db.query(FavoritesContract.FavoriteEntry.TABLE_NAME,
                columns, filter, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }
    public class LoaderReviewCallbacks implements LoaderManager.LoaderCallbacks<List<Review>> {
        @Override
        public Loader<List<Review>> onCreateLoader(int loaderId, Bundle bundle) {
            Uri baseUri = Uri.parse(REVIEW_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("api_key", API_KEY);
            return new ReviewLoader(getContext(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
            emptyReview.setText(R.string.empty);
            reviewAdapter.clear();
            mProgressBar.setVisibility(View.GONE);

            if (reviews != null && !reviews.isEmpty()) {
                reviewAdapter.addAll(reviews);
            }
        }
        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {
            reviewAdapter.clear();
        }
    }

    public class LoaderTrailerCallbacks implements LoaderManager.LoaderCallbacks<List<Trailer>> {
        @Override
        public Loader<List<Trailer>> onCreateLoader(int id, Bundle bundle) {
            Uri baseUri = Uri.parse(TRAILER_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("api_key", API_KEY);
            return new TrailerLoader(getContext(), uriBuilder.toString());
        }
        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailers) {
            emptyTrailer.setText(R.string.empty);
            trailerAdapter.clear();
            mProgressBar.setVisibility(View.GONE);

            if (trailers != null && !trailers.isEmpty()) {
                trailerAdapter.addAll(trailers);
            }
        }
        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {
            trailerAdapter.clear();
        }
    }
}
