package com.example.phoen.popularmovies.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.phoen.popularmovies.models.TMDB;


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
}
