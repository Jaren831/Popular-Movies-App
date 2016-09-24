package com.example.android.moviefragment;

        import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class GridFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_fragment, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        MovieAdapter movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailFragment detailFragment = new DetailFragment();
                Bundle args = new Bundle();
                args.putInt("position", position);
                detailFragment.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }
}
