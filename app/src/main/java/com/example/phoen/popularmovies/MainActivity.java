package com.example.phoen.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //starting data
        List<Movie> mMovies = new ArrayList<Movie>();
        Uri uri = Uri.parse("android.resource://com.example.phoen.popularmovies/drawable/movie01.png");
        mMovies.add(new Movie(1,uri.toString(),"Movie 1"));
        mMovies.add(new Movie(2,uri.toString(),"Movie 2"));
        mMovies.add(new Movie(3,uri.toString(),"Movie 3"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new MyRecyclerViewAdapter(this,mMovies);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG","Clicked " + adapter.getItem(position).getTitle() + " at position " + position);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return (null!=cm.getActiveNetworkInfo() && cm.getActiveNetworkInfo().isConnectedOrConnecting());
    }
}
