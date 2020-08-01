package com.morimoku.project_popular_movies2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Movie[] MoviesJSONParse(Context context,String moviePosterJSONStr) throws JSONException {
       final String LOG_TAG = "FetchingMovieDataURL";
        final String urlImage = "https://image.tmdb.org/t/p/w185";
        final String Id = "id";
        final String ORIGINAL_TITLE = "original_title";
       final String RELEASE_DATE = "release_date";
        final String VOTE_AVERAGE ="vote_average";
        final String OVERVIEW ="overview";
         final String POSTER_PATH ="poster_path";
        final String RESULT = "results";
        final String PARAM_LANGUAGE = "language";
       final String language = "en-US";
        JSONObject moviesNames = new JSONObject(moviePosterJSONStr);
        JSONArray moviesArray = moviesNames.getJSONArray(RESULT);
        Movie [] movieResults = new Movie[moviesArray.length()];


        for (int i = 0; i< moviesArray.length();i++){
            String poster_path,vote_average,overview,release_date,title;
            int id;
            Movie movie = new Movie();
            id= moviesArray.getJSONObject(i).optInt(Id);
            poster_path = moviesArray.getJSONObject(i).optString(POSTER_PATH);
            title = moviesArray.getJSONObject(i).optString(ORIGINAL_TITLE);
            release_date = moviesArray.getJSONObject(i).optString(RELEASE_DATE);
            vote_average= moviesArray.getJSONObject(i).optString(VOTE_AVERAGE);
            overview = moviesArray.getJSONObject(i).optString(OVERVIEW);



            movie.setMovieId(id);
            movie.setMoviePosterPath(urlImage + poster_path);
            movie.setMovieTitle(title);
            movie.setMovieReleaseDate(release_date);
            movie.setMovieVoteAverage(vote_average);
            movie.setMovieOverview(overview);

            movieResults[i] = movie;

        }

        return movieResults;

    }
    public static Review[] ReviewJSONParse(Context context ,String json) throws JSONException{

        final String PARAMS_REVIEWS = "results";
        final String PARAMS_AUTHOR = "author";
        final String PARAMS_CONTENT = "content";

        JSONObject reviewJSON = new JSONObject(json);
        JSONArray reviewArray = reviewJSON.getJSONArray(PARAMS_REVIEWS);
        Review[] reviewResult = new Review[reviewArray.length()];

        for (int i = 0; i< reviewArray.length();i++){
            String review_author, review_content;

            Review review = new Review();

            review_author = reviewArray.getJSONObject(i).optString(PARAMS_AUTHOR);
            review_content = reviewArray.getJSONObject(i).optString(PARAMS_CONTENT);

            review.setAuthor(review_author);
            review.setContent(review_content);

            reviewResult[i] = review;
        }

        return reviewResult;
    }

}
