package com.morimoku.project_popular_movies2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.morimoku.project_popular_movies2.FavouritesVideo.FavoritesContract.FavouritesAdd.TABLE_NAME;

public class FavouritesVideo extends ContentProvider {

    public static final String AUTHORITY = "com.morimoku.project_popular_movies2";
    public static final int FAVOURITES = 7;
    public static final int FAVOURITES_WITH_ID = 17;
    public static final String PATH_FAVORITES = "favorites";
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,PATH_FAVORITES,FAVOURITES);
        uriMatcher.addURI(AUTHORITY,PATH_FAVORITES+"/#",FAVOURITES_WITH_ID);
        return uriMatcher;
    }

private DatabaseSupport mDatabaseSupport;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDatabaseSupport = new DatabaseSupport(context);
        return true;


    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase mLiteOpenHelper = mDatabaseSupport.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match){
            case FAVOURITES:
                returnCursor = mLiteOpenHelper.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "id=?";
                String[] mSelectionArgs = new String[]{id};
                returnCursor = mLiteOpenHelper.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);

        }
returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match){
            //If accessing for FAVOURITES it is accessing macro data. If _WITH_ID it is accessing micro data.
            case FAVOURITES:
                return "vnd.android.cursor.dir"+ "/" + FavoritesContract.AUTHORITY + "/" + FavoritesContract.PATH_FAVOURITES;

            case FAVOURITES_WITH_ID:
                        return "vnd.android.cursor.item" +"/" + FavoritesContract.AUTHORITY + "/" + FavoritesContract.PATH_FAVOURITES;
            default:
                throw new UnsupportedOperationException("Unknown uri:" +uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase database = mDatabaseSupport.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case FAVOURITES:
                long id = database.insert(TABLE_NAME,null,values);
                if (id > 0 ){
                    returnUri = ContentUris.withAppendedId(FavoritesContract.FavouritesAdd.CONTENT_URI,id);
                }else {
                    throw new android.database.SQLException("Failed to insert row to into"+ uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = mDatabaseSupport.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int favouritesDeleted;

        switch (match){
            case FAVOURITES:
                favouritesDeleted = database.delete(TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        if (favouritesDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return favouritesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int favouritesUpdated;
        int match = sUriMatcher.match(uri);

        switch (match){
            case FAVOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                favouritesUpdated = mDatabaseSupport.getWritableDatabase().update(TABLE_NAME,values,"_id=?",new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if (favouritesUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);

        }
        return favouritesUpdated;
    }

    public static final class FavoritesContract{

        public static final String AUTHORITY = "com.morimoku.project_popular_movies2";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);
        public static final String PATH_FAVOURITES = "favourites";


        public static final class FavouritesAdd implements BaseColumns{
            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

            public static final String TABLE_NAME = "favourites";
            public static final String COL_ID = "movieId";
            public static final String COL_NAME = "movieName";
            public static final String COL_POSTER = "moviePoster";
            public static final String COL_RATE = "movieRate";
            public static final String COL_RELEASE = "movieRelease";
            public static final String COL_OVERVIEW = "movieOverview";

        }

    }
}
