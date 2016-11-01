package com.example.android.moviefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.android.moviefragment.R.id.detail_image;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class DetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        Bundle bundle = this.getArguments();
        Movie currentMovie = bundle.getParcelable("selectedMovie");


        ImageView movieImage = (ImageView) rootView.findViewById(detail_image);
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
        detailRating.setText(Integer.toString(currentMovie.getRating()) + " / 10");

        TextView detailDate = (TextView) rootView.findViewById(R.id.detail_date);
        detailDate.setText(currentMovie.getDate());

        return rootView;

    }
}
