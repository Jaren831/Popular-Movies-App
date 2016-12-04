package com.example.android.moviefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    public TrailerAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
        }

        Trailer currentTrailer = getItem(position);

        TextView trailerName = (TextView) listItemView.findViewById(R.id.trailer_text);
        trailerName.setText(currentTrailer.getTrailerName());

        return listItemView;
    }
}
