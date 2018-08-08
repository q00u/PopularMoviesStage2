package com.example.phoen.popularmovies.fragments;

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
import com.example.phoen.popularmovies.adapters.ReviewViewAdapter;
import com.example.phoen.popularmovies.models.ReviewResult;
import com.example.phoen.popularmovies.models.Reviews;
import com.example.phoen.popularmovies.rest.ApiClient;
import com.example.phoen.popularmovies.rest.ApiInterface;
import com.example.phoen.popularmovies.utils.OutlineTextView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewsFragment extends Fragment {
    public static final String ARGS = "REVIEWS_ARG";

    private int movieID;
    private int reviewsCount = 0;
    private ReviewViewAdapter reviewAdapter;
    private DetailsActivity detailsActivity;

    public static ReviewsFragment newInstance(int mID) {
        Bundle args = new Bundle();
        args.putInt(ARGS,mID);
        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movieID = getArguments().getInt(ARGS);
            Log.d("ReviewsFragment","Movie ID set by ARGS to " + movieID);
        } else {
            movieID = 0;
        }
    }

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsActivity = (DetailsActivity) getActivity();
        if (0==movieID) {
            movieID = detailsActivity.getmID();
            Log.d("ReviewsFragment","Movie ID set by mID to " + movieID);
        }

        final OutlineTextView reviewsTitle = getView().findViewById(R.id.ReviewsTitle);

        final RecyclerView reviewRecyclerView = getView().findViewById(R.id.Reviews);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(detailsActivity));

        if (0!=movieID) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Reviews> call;
            call = apiService.getMovieReviews(movieID);

            call.enqueue(new Callback<Reviews>() {
                @Override
                public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                    Log.d("ReviewsFragment", "Calling retrofit for " + movieID);
                    List<ReviewResult> mReviews = Objects.requireNonNull(response.body()).getResults();
                    reviewsCount = mReviews.size();
                    Log.d("ReviewsFragment", "Reviews received: " + reviewsCount);
                    reviewsTitle.setText(getResources().getQuantityString(R.plurals.numberOfReviews, reviewsCount, reviewsCount));
                    //detailsActivity.setReviewsCount(reviewsCount);
                    reviewAdapter = new ReviewViewAdapter(detailsActivity, mReviews);
                    reviewRecyclerView.setAdapter(reviewAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
                    Log.e("ReviewsFragment", t.toString());
                }
            });
        }
    }
}
