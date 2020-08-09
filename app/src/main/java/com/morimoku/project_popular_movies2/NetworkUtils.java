package com.morimoku.project_popular_movies2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class
NetworkUtils {


    final static String THEMOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String PARAM_API_KEY = "api_key";
    final static String apiKey = "KEY_HERE";
    final static String PARAM_LANGUAGE = "language";
    final static String language = "en-US";
    final static String videos = "videos";

    public static URL buildUrl(String queryType){
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath(queryType)
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    //following code is taken from the github-repo-search exercise.
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildDataUrl(int id) {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(id))
                .appendEncodedPath(videos)
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;

    }
}


