package com.morimoku.project_popular_movies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMoviePosterDisplay = (ImageView)findViewById(R.id.movie_poster);
        mMovieTitleDisplay = (TextView)findViewById(R.id.detail_title);
        mMovieRateDisplay = (TextView)findViewById(R.id.rate);
        mMovieReleaseDisplay  = (TextView)findViewById(R.id.release_date);
        mMoviePlotSynopsisDisplay = (TextView)findViewById(R.id.plot_synopsis);

        mRecyclerViewMovieDetail = (RecyclerView)findViewById(R.id.recyclerview_movies);


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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewMovieDetail.setLayoutManager(linearLayoutManager);

        mRecyclerViewMovieDetail.setHasFixedSize(true);
        
        mRecyclerDetailAdapter = new RecyclerDetailAdapter();
        
        mRecyclerViewMovieDetail.setAdapter(mRecyclerDetailAdapter);
        
        loadVideoData(); //TODO: Will have to add function to load data of video reputation of this video
    }

    private void loadVideoData() {

    }

}