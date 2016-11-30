package com.example.android.moviefragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviefragment.Data.FavoritesContract;
import com.example.android.moviefragment.Data.FavoritesDbHelper;

import java.io.ByteArrayOutputStream;

import static com.example.android.moviefragment.R.id.detail_image;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class DetailFragment extends Fragment {
    SQLiteDatabase db;
    FavoritesDbHelper mDbhelper;
    Bitmap bmp;
    ImageView movieImage;

    String movieId;
    String movieTitle;
    String moviePlot;
    String movieRating;
    String movieDate;

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
}
