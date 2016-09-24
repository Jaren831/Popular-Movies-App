package com.example.android.moviefragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jaren Lynch on 9/20/2016.
 */

class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item, parent, false);
        }
        Movie currentMovie = getItem(position);

        ImageButton movieImage = (ImageButton) gridItemView.findViewById(R.id.movie_image);
        byte[] movie_image = currentMovie.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(movie_image, 0, movie_image.length);
        movieImage.setImageBitmap(bitmap);

        TextView movie_text = (TextView) gridItemView.findViewById(R.id.movie_title);
        movie_text.setText(currentMovie.getTitle());

        return gridItemView;
    }
}

