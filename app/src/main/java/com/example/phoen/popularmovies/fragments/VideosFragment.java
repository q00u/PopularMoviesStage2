package com.example.phoen.popularmovies.fragments;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoen.popularmovies.R;
import com.example.phoen.popularmovies.activities.DetailsActivity;
import com.example.phoen.popularmovies.adapters.VideoViewAdapter;
import com.example.phoen.popularmovies.models.VideoResult;
import com.example.phoen.popularmovies.models.Videos;
import com.example.phoen.popularmovies.rest.ApiClient;
import com.example.phoen.popularmovies.rest.ApiInterface;
import com.example.phoen.popularmovies.utils.OutlineTextView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment implements LifecycleOwner {
    public static final String ARGS = "VIDEOS_ARG";
    private LifecycleRegistry registry=new LifecycleRegistry(this);

    private int movieID;
    private int videosCount = 0;
    private VideoViewAdapter videoAdapter;
    private DetailsActivity detailsActivity;

    public static VideosFragment newInstance(int mID) {
        Bundle args = new Bundle();
        args.putInt(ARGS,mID);
        VideosFragment fragment = new VideosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null!=getArguments()) {
            movieID=getArguments().getInt(ARGS);
            Log.d("VideosFragment","Movie ID set by ARGS to " + movieID);
        } else {
            movieID=0;
        }

        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsActivity = (DetailsActivity) getActivity();
        if (0==movieID) {
            movieID=detailsActivity.getmID();
            Log.d("VideosFragment","Movie ID set by mID to " + movieID);
        }

        final OutlineTextView videosTitle = getView().findViewById(R.id.VideosTitle);

        final RecyclerView videosRecyclerView = getView().findViewById(R.id.Videos);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(detailsActivity));

        if (0!=movieID) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Videos> call;
            call = apiService.getMovieVideos(movieID);

            call.enqueue(new Callback<Videos>() {
                @Override
                public void onResponse(Call<Videos> call, Response<Videos> response) {
                    Log.d("VideosFragment","Calling retrofit for " + movieID);
                    List<VideoResult> mVideos = Objects.requireNonNull(response.body()).getResults();
                    videosCount=mVideos.size();
                    Log.d("VideosFragment","Videos received: " + videosCount);
                    videosTitle.setText(getResources().getQuantityString(R.plurals.numberOfVideos,videosCount,videosCount));
                    videoAdapter = new VideoViewAdapter(detailsActivity,mVideos,registry);
                    videosRecyclerView.setAdapter(videoAdapter);
                }

                @Override
                public void onFailure(Call<Videos> call, Throwable t) {
                    Log.e("VideosFragment",t.toString());
                }
            });
        }

    }

    //lifecycle stuff:

    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }

    @Override
    public void onStart() {
        super.onStart();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }
}
