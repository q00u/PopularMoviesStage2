package com.example.phoen.popularmovies.rest;

import com.example.phoen.popularmovies.models.Reviews;
import com.example.phoen.popularmovies.models.TMDB;
import com.example.phoen.popularmovies.models.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("top_rated")
    Call<TMDB> getTopRated(@Query("page") int page);

    @GET("top_rated")
    Call<TMDB> getTopRated();

    @GET("popular")
    Call<TMDB> getPopular(@Query("page") int page);

    @GET("popular")
    Call<TMDB> getPopular();

    @GET("{id}")
    Call<TMDB> getMovieDetails(@Path("id") int id);

    @GET("{id}/reviews")
    Call<Reviews> getMovieReviews(@Path("id") int id);

    @GET("{id}/videos")
    Call<Videos> getMovieVideos(@Path("id") int id);
}
