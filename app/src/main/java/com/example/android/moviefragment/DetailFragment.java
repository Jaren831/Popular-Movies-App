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
    TextView current;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
//        Bundle bundle = this.getArguments();
//        int position = bundle.getInt("position");
//        current = (TextView) rootView.findViewById(R.id.uh_what);
//        current.setText(Integer.toString(position));
//
//        TextView detailText = (TextView) detailView.findViewById(R.id.detail_title);
//        detailText.setText(currentMovie.getTitle());
//
//        TextView detailPlot = (TextView) detailView.findViewById(R.id.detail_plot);
//        detailPlot.setText(currentMovie.getPlot());
//
//        TextView detailRating = (TextView) detailView.findViewById(R.id.detail_rating);
//        detailRating.setText(currentMovie.getRating());
//
//        TextView detailPopularity = (TextView) detailView.findViewById(R.id.detail_date);
//        detailDate.setText(currentMovie.getRating());

        
        
        return rootView;

    }
}
