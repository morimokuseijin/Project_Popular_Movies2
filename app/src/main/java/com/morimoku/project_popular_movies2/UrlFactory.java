package com.morimoku.project_popular_movies2;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlFactory {
    final static String BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String PARAM_API_KEY = "api_key";
    final static String apikey = "<Put your API_KEY here>";
    final static String PARAM_LANGUAGE = "language";
    final static String language = "en-US";
    final static String videos = "videos";
    final static String reviews = "reviews";

    public static URL buildUrlReview(int movieId){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieId))
                .appendEncodedPath(reviews)
                .appendQueryParameter(PARAM_API_KEY,apikey)
                .appendQueryParameter(PARAM_LANGUAGE,language)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.v("Review_URL", String.valueOf(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    return url;

    }
}
