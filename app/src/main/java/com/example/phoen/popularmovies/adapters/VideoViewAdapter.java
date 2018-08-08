package com.example.phoen.popularmovies.adapters;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.phoen.popularmovies.R;
import com.example.phoen.popularmovies.models.VideoResult;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;

import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<VideoResult> mVideos;
    private Lifecycle lifecycle;

    public VideoViewAdapter(Context context, List<VideoResult> data, Lifecycle lifecycle) {
        this.mInflater=LayoutInflater.from(context);
        this.mVideos=data;
        this.lifecycle=lifecycle;
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    @NonNull
    @Override
    public VideoViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);
        youTubePlayerView.getPlayerUIController().showFullscreenButton(false);
        lifecycle.addObserver(youTubePlayerView);

        return new ViewHolder(youTubePlayerView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewAdapter.ViewHolder holder, int position) {
        holder.cueVideo(mVideos.get(position).getKey());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;
        private YouTubePlayer youTubePlayer;
        private String currentVideoId;

        ViewHolder(YouTubePlayerView playerView) {
            super(playerView);
            youTubePlayerView = playerView;

            youTubePlayerView.initialize(initializedYouTubePlayer ->
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        youTubePlayer=initializedYouTubePlayer;
                        youTubePlayer.cueVideo(currentVideoId,0);
                    }
                }), true
            );
        }

        void cueVideo(String videoId) {
            currentVideoId = videoId;
            if(null==youTubePlayer) return;
            youTubePlayer.cueVideo(videoId,0);
        }
    }
}
