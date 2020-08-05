package com.morimoku.project_popular_movies2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSupport extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favourites.db";

    public DatabaseSupport(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATES_FAVOURITES_DATABASE = " CREATE TABLE "+ FavouritesVideo.FavoritesContract.FavouritesAdd.TABLE_NAME + "("
                + FavouritesVideo.FavoritesContract.FavouritesAdd._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID + " TEXT NOT NULL,"
                + FavouritesVideo.FavoritesContract.FavouritesAdd.COL_NAME +  " TEXT NOT NULL,"
                + FavouritesVideo.FavoritesContract.FavouritesAdd.COL_POSTER + " TEXT NOT NULL,"
                + FavouritesVideo.FavoritesContract.FavouritesAdd.COL_RATE + " TEXT NOT NULL,"
                + FavouritesVideo.FavoritesContract.FavouritesAdd.COL_RELEASE + " TEXT NOT NULL, "
                + FavouritesVideo.FavoritesContract.FavouritesAdd.COL_OVERVIEW + "TEXT NOT NULL"
                +"); ";
        db.execSQL(SQL_CREATES_FAVOURITES_DATABASE);






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouritesVideo.FavoritesContract.FavouritesAdd.TABLE_NAME);
        onCreate(db);

    }
}
