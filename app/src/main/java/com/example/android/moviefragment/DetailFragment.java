package com.example.android.moviefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class DetailFragment extends Fragment {
    DetailAdapter detailAdapter;
    ListView detailView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        detailView = (ListView) rootView.findViewById(R.id.detail_list);
        detailAdapter = new DetailAdapter(getActivity(), new ArrayList<Movie>());
        detailView.setAdapter(detailAdapter);


        return rootView;

    }
}
