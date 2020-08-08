package com.morimoku.project_popular_movies2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private RecyclerView mRecyclerViewVideoDetail;
    private DetailActivityVideoAdapter mDetailActivityVideoAdapter;
    public final static String MOVIEDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342";
    final static String API_KEY_QUERY_PARAM = "api_key";
    private int id = 0;
    private Review[] jsonReviewData;
    FloatingActionButton mFloatingActionButton;
    String[] mProjection =
            {
                    FavouritesVideo.FavoritesContract.FavouritesAdd._ID,
                    FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID
            };
    private String mSelectionClause;
    private String[] mSelectionArgs = {""};
    private String title = "";
    private String poster = "";
    private String rate = "";
    private String release = "";
    private String overview = "";
    Uri mNewUri;
    private Data[] jsonVideoData;




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
        mFloatingActionButton = findViewById(R.id.fabFavourite);
        mRecyclerViewVideoDetail = findViewById(R.id.recyclerview_video_detail);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMovieFavourites(String.valueOf(id))){
                    removeFavourites(String.valueOf(id));
                    Context context = getApplicationContext();
                    mFloatingActionButton.setImageResource(R.drawable.ic_android_favourite);
                    Toast.makeText(context,"This movie was removed from your favourites list",Toast.LENGTH_LONG).show();
                }else {
                    addToFavourites(title,id,poster,rate,release,overview);
                    Context context = getApplicationContext();
                    mFloatingActionButton.setImageResource(R.drawable.ic_android_favourite_pressed);
                    Toast.makeText(context,"This movie was added to your favourites list",Toast.LENGTH_LONG).show();
                }
            }
        });

        id = getIntent().getIntExtra("id",0);
        poster = getIntent().getStringExtra("poster");
        title = getIntent().getStringExtra("title");
        rate = getIntent().getStringExtra("rate");
        release = getIntent().getStringExtra("release");
        overview = getIntent().getStringExtra("overview");


        mMovieTitleDisplay.setText(title);
        mMoviePlotSynopsisDisplay.setText(overview);
        mMovieRateDisplay.setText(rate);
        mMovieReleaseDisplay.setText(release);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mMoviePosterDisplay);

        LinearLayoutManager VideoLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewVideoDetail.setLayoutManager(VideoLinearLayoutManager);
        mRecyclerViewVideoDetail.setHasFixedSize(true);//TODO: have to make an adapter!
        mRecyclerViewVideoDetail.setAdapter(mDetailActivityVideoAdapter);





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewMovieDetail.setLayoutManager(linearLayoutManager);

        mRecyclerViewMovieDetail.setHasFixedSize(true);
        
        mRecyclerViewMovieDetail.setAdapter(mRecyclerDetailAdapter);
        
        //loadVideoData(); //TODO: Will have to add function to load data of video reputation of this video
        loadReviewData();
        isMovieFavourites(String.valueOf(id));
    }

    private void removeFavourites(String id) {
        mSelectionClause = FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID + " LIKE ?";
        String[] selectionArgs = new String[]{id};

        getContentResolver().delete(
                FavouritesVideo.FavoritesContract.FavouritesAdd.CONTENT_URI,
                mSelectionClause,
                selectionArgs
        );

    }

    private void loadReviewData() {
    String reviewId = String.valueOf(id);
    new FetchReview().execute(reviewId);

    }


    private void loadVideoData() {
        String dataId = String.valueOf(id);
        new Execute().execute(dataId);

    }
    private void addToFavourites(String name, int id, String poster, String rate, String release, String overview){

        ContentValues mContentValues = new ContentValues();
        mContentValues.put(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID, id);
        mContentValues.put(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_NAME, name);
        mContentValues.put(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_POSTER, poster);
        mContentValues.put(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_RATE, rate);
        mContentValues.put(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_RELEASE, release);
        mContentValues.put(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_OVERVIEW, overview);

        mNewUri = getContentResolver().insert(
                FavouritesVideo.FavoritesContract.FavouritesAdd.CONTENT_URI,
                mContentValues
        );



    }

    public boolean isMovieFavourites(String id){
        mSelectionClause = FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID + " = ?";
        mSelectionArgs[0] =id;
        Cursor mCursor = getContentResolver().query(
                FavouritesVideo.FavoritesContract.FavouritesAdd.CONTENT_URI,
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                null);
        if (mCursor.getCount() <= 0){
            mCursor.close();
            mFloatingActionButton.setImageResource(R.drawable.ic_android_favourite);
            return false;
        }
        mCursor.close();
        mFloatingActionButton.setImageResource(R.drawable.ic_android_favourite_pressed);
        return true;


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

    private class Execute extends AsyncTask<String,Void,Data[]>{


        @Override
        protected Data[] doInBackground(String... strings) {
            if (strings.length == 0){
                return null;
            }
            URL movieDataUrl = NetworkUtils.buildDataUrl(id);
            try {
                String jsonDataResponse = NetworkUtils.getResponseFromHttpUrl(movieDataUrl);

                jsonVideoData = JsonUtils.getDataInformationData(DetailActivity.this,jsonDataResponse);

                return jsonVideoData;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(Data[] data) {
            if (data != null){
                mDetailActivityVideoAdapter = new DetailActivityVideoAdapter(data, DetailActivity.this);
                mRecyclerViewVideoDetail.setAdapter(mDetailActivityVideoAdapter);

            }
        }
    }
}