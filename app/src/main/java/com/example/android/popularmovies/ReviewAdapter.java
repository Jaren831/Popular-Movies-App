package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(com.example.android.popularmovies.R.layout.review_item, parent, false);
        }

        Review currentReview = getItem(position);

        TextView authorView = (TextView) listItemView.findViewById(com.example.android.popularmovies.R.id.review_author);
        authorView.setText(currentReview.getAuthor());

        TextView reviewView = (TextView) listItemView.findViewById(com.example.android.popularmovies.R.id.review_text);
        reviewView.setText(currentReview.getReview());

        return listItemView;
    }
}
