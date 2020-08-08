package com.morimoku.project_popular_movies2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouritesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FavouritesListAdapter mFavouritesAdapter;
    private static final int FAVOURITE_LOADER = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie_favourites);
        int mNoOfColumns = MainActivity.calculateNoOfColumns(getApplicationContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,mNoOfColumns);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mFavouritesAdapter = new FavouritesListAdapter(this);
        mRecyclerView.setAdapter(mFavouritesAdapter);

        getSupportLoaderManager();





    }
}