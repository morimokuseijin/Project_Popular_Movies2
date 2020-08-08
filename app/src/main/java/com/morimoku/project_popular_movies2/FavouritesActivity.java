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
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";

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
        mFavouritesAdapter.notifyDataSetChanged();

        getSupportLoaderManager().initLoader(FAVOURITE_LOADER,null,this);





    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mFavData = null;

            @Override
            protected void onStartLoading() {
                if (mFavData != null){
                    deliverResult(mFavData);
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

            public void deliverResult(Cursor data) {
                mFavData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
       mFavouritesAdapter.renewData(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavouritesAdapter.renewData(null);
    }

    //https://stackoverflow.com/questions/28236390/recyclerview-store-restore-state-between-activities
    @Override
    protected void onPause() {
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            getSupportLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}