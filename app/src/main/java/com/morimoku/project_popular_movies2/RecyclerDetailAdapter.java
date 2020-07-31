package com.morimoku.project_popular_movies2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerDetailAdapter.RecyclerDetailAdapterHolder>{


    public RecyclerDetailAdapter(){}


    public class RecyclerDetailAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mDetailTextView;

        public RecyclerDetailAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mDetailTextView = (TextView)itemView.findViewById(R.id.tv_detail_list);
        }

        @Override
        public void onClick(View v) {
            //clickListener.onReviewItemClick(reviews.get(getAdapterPosition()));
        }
    }
    @NonNull
    @Override
    public RecyclerDetailAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.detail_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;
        View view = layoutInflater.inflate(layoutIdForListItem,parent,shouldAttachImmediately);
        return new RecyclerDetailAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDetailAdapterHolder holder, int position) {

    }




    @Override
    public int getItemCount() {
        return 0;
    }
}
