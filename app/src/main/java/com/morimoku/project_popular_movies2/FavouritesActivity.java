package com.morimoku.project_popular_movies2;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView mRecyclerView;
    FavouritesListAdapter mFavouritesAdapter;
    private static final int FAVOURITE_LOADER = 77;
    private static Bundle mBundle;
    private final String KEY_RECYCLER = "recyclerview_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mRecyclerView = findViewById(R.id.recyclerview_movie_favourites);
        int mNoOfColumns = MainActivity.calculateNoOfColumns(getApplicationContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,mNoOfColumns);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mFavouritesAdapter = new FavouritesListAdapter(this);
        mRecyclerView.setAdapter(mFavouritesAdapter);
        mFavouritesAdapter.notifyDataSetChanged();

        getSupportLoaderManager().initLoader(FAVOURITE_LOADER,null,this);





    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mData = null;

            @Override
            protected void onStartLoading() {
                if (mData != null){
                    Result(mData);
                } else {
                    forceLoad();
                }
            }


            @Override
            public Cursor loadInBackground() {
                try{

                    return getContentResolver().query(FavouritesVideo.FavoritesContract.FavouritesAdd.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            public void Result(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavouritesAdapter.renewData(null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mFavouritesAdapter.renewData(data);
    }


    //Reference: https://stackoverflow.com/questions/28236390/recyclerview-store-restore-state-between-activities
    @Override
    protected void onPause() {
        mBundle = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundle.putParcelable(KEY_RECYCLER, listState);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundle != null) {
            getSupportLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);
            Parcelable listState = mBundle.getParcelable(KEY_RECYCLER);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}