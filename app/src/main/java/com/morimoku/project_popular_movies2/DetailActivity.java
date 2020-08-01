package com.morimoku.project_popular_movies2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ImageView mMoviePosterDisplay;
    TextView mMovieTitleDisplay;
    TextView mMovieRateDisplay;
    TextView mMovieReleaseDisplay;
    TextView mMoviePlotSynopsisDisplay;
    private RecyclerView mRecyclerViewMovieDetail;
    private RecyclerDetailAdapter mRecyclerDetailAdapter;
    public final static String MOVIEDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342";
    final static String API_KEY_QUERY_PARAM = "api_key";
    private int id = 0;
    private Review[] jsonReviewData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMoviePosterDisplay = findViewById(R.id.movie_poster);
        mMovieTitleDisplay = findViewById(R.id.detail_title);
        mMovieRateDisplay = findViewById(R.id.rate);
        mMovieReleaseDisplay  = findViewById(R.id.release_date);
        mMoviePlotSynopsisDisplay = findViewById(R.id.plot_synopsis);

        mRecyclerViewMovieDetail = findViewById(R.id.recyclerview_movie_detail);

        id = getIntent().getIntExtra("id",0);
        String poster = getIntent().getStringExtra("poster");
        String title = getIntent().getStringExtra("title");
        String rate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");


        mMovieTitleDisplay.setText(title);
        mMoviePlotSynopsisDisplay.setText(overview);
        mMovieRateDisplay.setText(rate);
        mMovieReleaseDisplay.setText(release);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mMoviePosterDisplay);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewMovieDetail.setLayoutManager(linearLayoutManager);

        mRecyclerViewMovieDetail.setHasFixedSize(true);
        
        mRecyclerViewMovieDetail.setAdapter(mRecyclerDetailAdapter);
        
        //loadVideoData(); //TODO: Will have to add function to load data of video reputation of this video
        loadReviewData();
    }

    private void loadReviewData() {
    String reviewId = String.valueOf(id);
    new FetchReview().execute(reviewId);

    }


    private void loadVideoData() {

    }

    private class FetchReview extends AsyncTask<String,Void,Review[]>{
        @Override
        protected Review[] doInBackground(String... strings) {
            if (strings.length == 0){
                return null;
            }
            URL movieRequestUrl = UrlFactory.buildUrlReview(id);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                jsonReviewData = JsonUtils.ReviewJSONParse(DetailActivity.this,jsonMovieResponse);
                return jsonReviewData;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Review[] reviews) {
            if (reviews != null){
                mRecyclerDetailAdapter = new RecyclerDetailAdapter(reviews);
                mRecyclerViewMovieDetail.setAdapter(mRecyclerDetailAdapter);
            }
        }
    }
}