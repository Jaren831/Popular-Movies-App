package com.example.android.moviefragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaren Lynch on 9/23/2016.
 */

public class GridFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Movie>> {
    GridAdapter gridAdapter;
    GridView gridView;
    TextView emptyView;
    ProgressBar mProgressBar;
    private static final int MOVIE_LOADER_ID = 1;
    public static final String LOG_TAG = MovieQuery.class.getSimpleName();
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/discover/movie?api_key=42e95964a6932d7d9b7d25f1c0c70c01";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_fragment, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridAdapter = new GridAdapter(getActivity(), new ArrayList<Movie>());
        emptyView = (TextView) rootView.findViewById(R.id.empty);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();


        gridView.setAdapter(gridAdapter);

        while (gridView == null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        getActivity();
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.noInternet);
        }

        gridView.setEmptyView(emptyView);

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

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(MOVIE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("sort_by", orderBy);
        Log.i(LOG_TAG, uriBuilder.toString());


        return new MovieLoader(this.getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        emptyView.setText(R.string.empty);
        // Clear the adapter of previous earthquake data
        gridAdapter.clear();
        mProgressBar.setVisibility(View.GONE);

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.

        if (movies != null && !movies.isEmpty()) {
            gridAdapter.addAll(movies);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(2, null, this);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        gridAdapter.clear();
    }
}
