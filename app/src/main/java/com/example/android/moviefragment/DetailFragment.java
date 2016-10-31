package com.example.android.moviefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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




        TextView detailText = (TextView) rootView.findViewById(R.id.detail_title);
        detailText.setText(currentMovie.getTitle());

        TextView detailPlot = (TextView) rootView.findViewById(R.id.detail_plot);
        detailPlot.setText(currentMovie.getPlot());

        TextView detailRating = (TextView) rootView.findViewById(R.id.detail_rating);
        detailRating.setText(currentMovie.getRating());

        TextView detailDate = (TextView) rootView.findViewById(R.id.detail_date);
        detailDate.setText(currentMovie.getRating());

        
        
        return rootView;

    }
}
