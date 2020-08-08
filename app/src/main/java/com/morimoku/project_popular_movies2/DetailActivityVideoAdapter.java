package com.morimoku.project_popular_movies2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailActivityVideoAdapter extends RecyclerView.Adapter <DetailActivityVideoAdapter.DetailVideoView> {

    private TextView mVideoDetail;
    private Data[] mVideoData;
    final String BASE_YOUTUBE_LINK = "https://www.youtube.com/watch?v=";
    Context context;

    public DetailActivityVideoAdapter(Data[] data, Context context) {
        mVideoData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailVideoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutVideoDetail = R.layout.detail_video_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(LayoutVideoDetail,parent,false);
        return new DetailVideoView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailVideoView holder, int position) {
        String VideoBind = mVideoData[position].getName();
        final String VideoLink = mVideoData[position].getKey();

        mVideoDetail.setText(VideoBind);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri videoUri = Uri.parse(BASE_YOUTUBE_LINK + VideoLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, videoUri);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (null == mVideoData) {
            return 0;
        }
        return mVideoData.length;
    }

    class DetailVideoView extends RecyclerView.ViewHolder implements View.OnClickListener{

        public DetailVideoView(@NonNull View itemView) {
            super(itemView);
            mVideoDetail = itemView.findViewById(R.id.recyclerview_video_detail);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
