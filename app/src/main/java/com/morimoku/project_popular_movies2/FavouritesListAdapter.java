package com.morimoku.project_popular_movies2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//TODO: This adapter is for the video favourites list. We would need to implement it for listing the data of favourites.

public class FavouritesListAdapter extends RecyclerView.Adapter <FavouritesListAdapter.FavouritesViewAdapter> {

    private Context context;
    public FavouritesListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavouritesViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewAdapter holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class FavouritesViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        public FavouritesViewAdapter(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
