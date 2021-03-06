package com.example.android.popularmovies;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    Boolean favorites;
    private static final int MOVIE_LOADER_ID = 1;
    public static final String MOVIE_URL = "http://api.themoviedb.org/3/movie";
    public static final String API_KEY = "42e95964a6932d7d9b7d25f1c0c70c01";
    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            settingsReload();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.example.android.popularmovies.R.layout.grid_fragment, container, false);
        gridView = (GridView) rootView.findViewById(com.example.android.popularmovies.R.id.movie_grid);
        gridAdapter = new GridAdapter(getActivity(), new ArrayList<Movie>());
        emptyView = (TextView) rootView.findViewById(com.example.android.popularmovies.R.id.empty);
        mProgressBar = (ProgressBar) rootView.findViewById(com.example.android.popularmovies.R.id.progressBar);
        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.registerOnSharedPreferenceChangeListener(listener);

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
            emptyView.setText(com.example.android.popularmovies.R.string.noInternet);
        }

        gridView.setEmptyView(emptyView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailFragment detailFragment = new DetailFragment();
                Bundle args = new Bundle();
                Movie currentMovie = (Movie) parent.getAdapter().getItem(position);
                args.putParcelable("selectedMovie", currentMovie);
                detailFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(com.example.android.popularmovies.R.id.fragment_container, detailFragment);
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
                getString(com.example.android.popularmovies.R.string.settings_order_by_key),
                getString(com.example.android.popularmovies.R.string.settings_order_by_default));

        String show = sharedPrefs.getString(
                getString(com.example.android.popularmovies.R.string.settings_show_key),
                getString(com.example.android.popularmovies.R.string.settings_show_default));

        switch (show) {
            case "all":
                favorites = false;
                break;
            case "favorites":
                favorites = true;
                break;
        }

        Uri baseUri = Uri.parse(MOVIE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(orderBy);
        uriBuilder.appendQueryParameter("api_key", API_KEY);
        return new MovieLoader(this.getContext(), uriBuilder.toString(), favorites);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        emptyView.setText(com.example.android.popularmovies.R.string.empty);
        gridAdapter.clear();
        mProgressBar.setVisibility(View.GONE);

        if (movies != null && !movies.isEmpty()) {
            gridAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        gridAdapter.clear();
    }
    public void settingsReload() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(1, null, this);
        mProgressBar.setVisibility(View.VISIBLE);
        Toast updateToast = Toast.makeText(getActivity(), "Settings Saved", Toast.LENGTH_SHORT);
        updateToast.show();
    }
}
