package com.morimoku.project_popular_movies2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class FavouritesDetail extends AppCompatActivity {

    private int id = 0;
    private String title = "";
    private String poster = "";
    private String rate = "";
    private String release = "";
    private String overview = "";
    Uri mNewUri;
    private String mSelectionClause;
    private String[] mSelectionArgs = {""};
    String[] mProjection =
            {
                    FavouritesVideo.FavoritesContract.FavouritesAdd._ID,
                    FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID
            };
    FloatingActionButton fab;
    private SQLiteDatabase mDb;
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerReview;
    private DetailActivityVideoAdapter mDetailActivityVideo;
    private RecyclerDetailAdapter mRecyclerDetailAdapter;
    TextView textViewName;
    TextView textViewDate;
    TextView textViewRate;
    ImageView imageView;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        textViewName = (TextView) findViewById(R.id.textView5);
        textViewDate = (TextView) findViewById(R.id.textView6);
        textViewRate = (TextView) findViewById(R.id.textView7);
        imageView = (ImageView) findViewById(R.id.imageView_favourites_video);
        description = (TextView) findViewById(R.id.FavouritesVideoListtextview);



        DatabaseSupport databaseSupport = new DatabaseSupport(this);
        mDb = databaseSupport.getWritableDatabase();

        mRecyclerView = (RecyclerView) findViewById(R.id.FavouritesVideoList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mDetailActivityVideo);

        mRecyclerReview = (RecyclerView) findViewById(R.id.FavouritesVideoReview);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        mRecyclerReview.setLayoutManager(linearLayoutManager1);
        mRecyclerReview.setHasFixedSize(true);
        mRecyclerReview.setAdapter(mRecyclerDetailAdapter);//Adding RecyclerView function for review of the video.We would have to add this function to completely move the application.





        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMovieFavourites(String.valueOf(id))){
                    removeFavourites(String.valueOf(id));
                    Context context = getApplicationContext();
                    fab.setImageResource(R.drawable.ic_android_favourite);
                    Toast.makeText(context,"This movie was removed from your favourites list",Toast.LENGTH_LONG).show();
                }else {
                    addToFavourites(title,id,poster,rate,release,overview);
                    Context context = getApplicationContext();
                    fab.setImageResource(R.drawable.ic_android_favourite_pressed);
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

        textViewName.setText(title);
        textViewRate.setText(rate);
        textViewDate.setText(release);
        description.setText(overview);

        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);



        isMovieFavourites(String.valueOf(id));
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
            fab.setImageResource(R.drawable.ic_android_favourite);
            return false;
        }
        mCursor.close();
        fab.setImageResource(R.drawable.ic_android_favourite_pressed);
        return true;


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

}