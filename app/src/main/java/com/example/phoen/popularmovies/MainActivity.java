package com.example.phoen.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.phoen.popularmovies.models.Result;
import com.example.phoen.popularmovies.models.TMDB;
import com.example.phoen.popularmovies.rest.ApiClient;
import com.example.phoen.popularmovies.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new InternetCheck(this, "com.example.phoen.popularmovies.InternetError", true).execute();

         final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TMDB> call = apiService.getTopRated();
        call.enqueue(new Callback<TMDB>() {
            @Override
            public void onResponse(Call<TMDB> call, Response<TMDB> response) {
                List<Result> mMovies = response.body().getResults();
                Log.d("Main","Movies received: " + mMovies.size());
                adapter = new MyRecyclerViewAdapter(MainActivity.this,mMovies);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TMDB> call, Throwable t) {
                Log.e("Main",t.toString());
            }
        });

//        //starting data
//        List<Movie> mMovies = new ArrayList<Movie>();
//        Uri uri = Uri.parse("android.resource://com.example.phoen.popularmovies/drawable/movie01.png");
//        mMovies.add(new Movie(1,uri.toString(),"Movie 1"));
//        mMovies.add(new Movie(2,uri.toString(),"Movie 2"));
//        mMovies.add(new Movie(3,uri.toString(),"Movie 3"));




    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG","Clicked " + adapter.getItem(position).getTitle() + " at position " + position);
    }


}
