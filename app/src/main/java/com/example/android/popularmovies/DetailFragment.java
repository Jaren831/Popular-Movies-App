package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Data.FavoritesContract;
import com.example.android.popularmovies.Data.FavoritesDbHelper;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.android.popularmovies.R.id.detail_image;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class DetailFragment extends Fragment {


    public static final String MOVIE_URL = "http://api.themoviedb.org/3/movie";
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
    TrailerAdapter trailerAdapter;
    ProgressBar trailerProgress;

    ListView reviewList;
    ReviewAdapter reviewAdapter;
    ProgressBar reviewProgress;

    TextView emptyReview;
    TextView emptyTrailer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(com.example.android.popularmovies.R.layout.detail_fragment, container, false);

        Bundle bundle = this.getArguments();
        final Movie currentMovie = bundle.getParcelable("selectedMovie");

        movieImage = (ImageView) rootView.findViewById(detail_image);
        if (currentMovie.getImage() != null) {
            movieImage.setImageBitmap(currentMovie.getImage());
        } else {
            movieImage.setImageResource(com.example.android.popularmovies.R.drawable.ic_help_outline_black_24dp);
        }

        TextView detailText = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.detail_title);
        detailText.setText(currentMovie.getTitle());

        TextView detailPlot = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.detail_plot);
        detailPlot.setSelected(true);
        detailPlot.setText(currentMovie.getPlot());

        TextView detailRating = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.detail_rating);
        detailRating.setText(currentMovie.getRating() + " / 10");

        TextView detailDate = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.detail_date);
        detailDate.setText(currentMovie.getDate());

        movieId = currentMovie.getID();
        bundle = new Bundle();
        bundle.putString("id", movieId);
        movieTitle = currentMovie.getTitle();
        moviePlot = currentMovie.getPlot();
        movieRating = currentMovie.getRating();
        movieDate = currentMovie.getDate();

        CheckBox favoriteCheck = (CheckBox) rootView.findViewById(com.example.android.popularmovies.R.id.favorite_check);
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

        reviewList = (ListView) rootView.findViewById(com.example.android.popularmovies.R.id.review_list);
        emptyReview = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.empty_review);
        reviewProgress = (ProgressBar) rootView.findViewById(com.example.android.popularmovies.R.id.progressBar_review);
        reviewAdapter = new ReviewAdapter(getContext());
        reviewList.setAdapter(reviewAdapter);

        trailerList = (ListView) rootView.findViewById(com.example.android.popularmovies.R.id.trailer_list);
        emptyTrailer = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.empty_trailer);
        trailerProgress = (ProgressBar) rootView.findViewById(com.example.android.popularmovies.R.id.progressBar_trailer);
        trailerAdapter = new TrailerAdapter(getContext());
        trailerList.setAdapter(trailerAdapter);

        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            getLoaderManager().initLoader(LOADER_REVIEW, bundle, new LoaderReviewCallbacks());
            getLoaderManager().initLoader(LOADER_TRAILER, bundle, new LoaderTrailerCallbacks());
        } else {
            reviewProgress.setVisibility(View.GONE);
            trailerProgress.setVisibility(View.GONE);
            emptyReview.setText(com.example.android.popularmovies.R.string.noInternet);
            emptyTrailer.setText(com.example.android.popularmovies.R.string.noInternet);
        }
        trailerList.setEmptyView(emptyTrailer);
        reviewList.setEmptyView(emptyReview);

        trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Trailer currentTrailer = trailerAdapter.getItem(position);
                Uri trailerUri = Uri.parse(getResources().getString(com.example.android.popularmovies.R.string.trailer_base_url) + currentTrailer.getYoutubeKey());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
                startActivity(websiteIntent);
            }
        });

        reviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Review currentReview = reviewAdapter.getItem(position);
                Uri reviewUri = Uri.parse(currentReview.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, reviewUri);
                startActivity(websiteIntent);
            }
        });


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
        public Loader<List<Review>> onCreateLoader(int id, Bundle bundle) {
            String reviewID = bundle.getString("id");
            Uri baseUri = Uri.parse(MOVIE_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(reviewID);
            uriBuilder.appendPath("reviews");
            uriBuilder.appendQueryParameter("api_key", API_KEY);
            return new ReviewLoader(getContext(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
            emptyReview.setText(com.example.android.popularmovies.R.string.empty_review);
            reviewAdapter.clear();
            reviewProgress.setVisibility(View.GONE);

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
            String trailerID = bundle.getString("id");
            Uri baseUri = Uri.parse(MOVIE_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(trailerID);
            uriBuilder.appendPath("videos");
            uriBuilder.appendQueryParameter("api_key", API_KEY);
            return new TrailerLoader(getContext(), uriBuilder.toString());
        }
        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailers) {
            emptyTrailer.setText(com.example.android.popularmovies.R.string.empty_trailers);
            trailerAdapter.clear();
            trailerProgress.setVisibility(View.GONE);

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
