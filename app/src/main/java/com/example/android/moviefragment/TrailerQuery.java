package com.example.android.moviefragment;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaren Lynch on 12/2/2016.
 */

public class TrailerQuery {
    public static final String LOG_TAG = TrailerQuery.class.getSimpleName();

    private TrailerQuery() {}


    public static List<Trailer> fetchTrailerData(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractTrailers(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error return code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the trailer JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.defaultCharset().forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Trailer> extractTrailers(String trailerJSON) {
        if (TextUtils.isEmpty(trailerJSON)) {
            return null;
        }
        List<Trailer> trailers = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(trailerJSON);
            JSONArray jsonArray = jsonRootObject.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentTrailer = jsonArray.getJSONObject(i);

                String trailerName = currentTrailer.optString("name");
                String youtubeKey = currentTrailer.optString("key");
                trailers.add(new Trailer(youtubeKey, trailerName));
            }
        } catch (JSONException e) {
            Log.e("TrailerQuery", "Problem parsing the trailer json results", e);
        }
        return trailers;
    }
}
