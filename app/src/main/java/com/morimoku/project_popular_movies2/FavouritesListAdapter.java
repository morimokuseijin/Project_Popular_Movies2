package com.morimoku.project_popular_movies2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


public class FavouritesListAdapter extends RecyclerView.Adapter <FavouritesListAdapter.FavouritesViewAdapter> {

    private Context context;
    private Cursor mCursor;
    public String name = "";
    public String poster = "";
    public int id;
    public FavouritesListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavouritesViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_favourites_list,parent,false);
        return new FavouritesViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewAdapter holder, int position) {
        int idOfFavourites = mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd._ID);
        int posterId = mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_POSTER);

        mCursor.moveToPosition(position);

        id = mCursor.getInt(idOfFavourites);
        poster = mCursor.getString(posterId);

        holder.itemView.setTag(id);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mFavourites);

    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return  mCursor.getCount();
    }

    public Cursor renewData(Cursor cursor){
        if (mCursor == cursor){
            return null;
        }
        Cursor newData = mCursor;
        this.mCursor = cursor;

        if (cursor != null){
            this.notifyDataSetChanged();
        }
    return newData;
    }


    class FavouritesViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mFavourites;

        public FavouritesViewAdapter(@NonNull View itemView) {
            super(itemView);
            mFavourites = (ImageView) itemView.findViewById(R.id.movie_ranking);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);

            String name = mCursor.getString(mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_NAME));
            int movieId = mCursor.getInt(mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_ID));
            String overview = mCursor.getString(mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_OVERVIEW));
            String rate = mCursor.getString(mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_RATE));
            String release = mCursor.getString(mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_RELEASE));
            String poster = mCursor.getString(mCursor.getColumnIndex(FavouritesVideo.FavoritesContract.FavouritesAdd.COL_POSTER));

            Intent startNewActivity = new Intent(context,FavouritesDetail.class);
            startNewActivity.putExtra(Intent.EXTRA_TEXT,adapterPosition);
            startNewActivity.putExtra("title",name);
            startNewActivity.putExtra("poster",poster);
            startNewActivity.putExtra("rate",rate);
            startNewActivity.putExtra("release",release);
            startNewActivity.putExtra("overview",overview);
            startNewActivity.putExtra("id",id);

            context.startActivity(startNewActivity);

        }
    }
}
