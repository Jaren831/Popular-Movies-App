package com.example.android.moviefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.moviefragment.R.id.detail_image;

/**
 * Created by Jaren Lynch on 10/30/2016.
 */

class DetailAdapter extends ArrayAdapter<Movie> {
    public DetailAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View detailView = convertView;
        if (detailView == null) {
            detailView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item, parent, false);
        }
        Movie currentMovie = getItem(position);

        ImageView detailImage = (ImageView) detailView.findViewById(detail_image);
        if (currentMovie.getImage() != null) {
            detailImage.setImageBitmap(currentMovie.getImage());
        } else {
            detailImage.setImageResource(R.drawable.ic_help_outline_black_24dp);
        }

        TextView detailText = (TextView) detailView.findViewById(R.id.detail_title);
        detailText.setText(currentMovie.getTitle());

        TextView detailPlot = (TextView) detailView.findViewById(R.id.detail_plot);
        detailPlot.setText(currentMovie.getPlot());

        TextView detailRating = (TextView) detailView.findViewById(R.id.detail_rating);
        detailRating.setText(currentMovie.getRating());

        TextView detailDate = (TextView) detailView.findViewById(R.id.detail_date);
        detailDate.setText(currentMovie.getDate());

        return detailView;
    }
}
