package com.example.android.moviefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class DetailFragment extends Fragment {
    TextView current;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position");
        current = (TextView) rootView.findViewById(R.id.uh_what);
        current.setText(Integer.toString(position));

        List<Movie> movies;

        TextView detailText = (TextView) rootView.findViewById(R.id.detail_title);
        detailText.setText(currentMovie.getTitle());

        TextView detailPlot = (TextView) rootView.findViewById(R.id.detail_plot);
        detailPlot.setText(currentMovie.getPlot());

        TextView detailRating = (TextView) rootView.findViewById(R.id.detail_rating);
        detailRating.setText(currentMovie.getRating());

        TextView detailPopularity = (TextView) rootView.findViewById(R.id.detail_popularity);


        return rootView;

    }
}
