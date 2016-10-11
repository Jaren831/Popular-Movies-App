package com.example.android.moviefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.moviefragment.R.id.movie_image;

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

        ImageView movieImage = (ImageView) gridItemView.findViewById(movie_image);
        if (currentMovie.getImage() != null) {
            movieImage.setImageBitmap(currentMovie.getImage());
        } else {
            movieImage.setImageResource(R.drawable.ic_help_outline_black_24dp);
        }

        TextView movie_text = (TextView) gridItemView.findViewById(R.id.movie_title);
        movie_text.setText(currentMovie.getTitle());

        return gridItemView;
    }
}

