package com.example.phoen.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.phoen.popularmovies.models.Result;
import com.example.phoen.popularmovies.models.TMDB;
import com.example.phoen.popularmovies.rest.ApiClient;
import com.example.phoen.popularmovies.rest.ApiInterface;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;

    private static final int SORT_POPULAR = 0;
    private static final int SORT_RATED = 1;

    private static int sortMethod = SORT_POPULAR;
    public static boolean showTitles = false;


    private void callTMDB() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TMDB> call;
        switch(sortMethod){
            case SORT_POPULAR:
                call = apiService.getPopular();
                break;
            case SORT_RATED:
                call = apiService.getTopRated();
                break;
            default:
                call = apiService.getTopRated();
                Toast.makeText(this,"Undefined sort, sorting by Top Rated instead",Toast.LENGTH_LONG).show();
        }
        call.enqueue(new Callback<TMDB>() {
            @Override
            public void onResponse(@NonNull Call<TMDB> call, @NonNull Response<TMDB> response) {
                List<Result> mMovies = Objects.requireNonNull(response.body()).getResults();
                Log.d("Main",(sortMethod==SORT_POPULAR?"POPULAR":"TOP") + " Movies received: " + mMovies.size());
                adapter = new MyRecyclerViewAdapter(MainActivity.this,mMovies);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<TMDB> call, @NonNull Throwable t) {
                Log.e("Main",t.toString());
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar options = findViewById(R.id.options);
        setSupportActionBar(options);

        new InternetCheck(this, "com.example.phoen.popularmovies.InternetError", true).execute();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        callTMDB();
    }

    @Override
    public void onItemClick(View view, int position) {
        Result movie = adapter.getItem(position);
        Log.i("TAG","Clicked " + movie.getTitle() + " at position " + position);

        Intent intent = new Intent(this,Details.class);
        intent.putExtra("MovieObject",movie);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //https://stackoverflow.com/questions/32969172/how-to-display-menu-item-with-icon-and-text-in-appcompatactivity
    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (null!=menu) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu,true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(),"onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
            menu.findItem(R.id.popular).setEnabled(SORT_POPULAR!=sortMethod);
            menu.findItem(R.id.rated).setEnabled(SORT_RATED!=sortMethod);
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                Toast.makeText(this,"Sorting by popularity",Toast.LENGTH_SHORT).show();
                sortMethod=SORT_POPULAR;
                Objects.requireNonNull(getSupportActionBar()).setTitle("Popular Movies");
                invalidateOptionsMenu();
                callTMDB();
                return true;
            case R.id.rated:
                Toast.makeText(this,"Sorting by highest rated",Toast.LENGTH_SHORT).show();
                sortMethod=SORT_RATED;
                Objects.requireNonNull(getSupportActionBar()).setTitle("Highest Rated Movies");
                invalidateOptionsMenu();
                callTMDB();
                return true;
            case R.id.titles:
                Toast.makeText(this,"Toggling title text",Toast.LENGTH_SHORT).show();
                showTitles=!showTitles;
                adapter.notifyDataSetChanged();
                return true;
            case R.id.larger:
                Toast.makeText(this,"Embiggening poster size",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.smaller:
                Toast.makeText(this,"Shrinking poster size",Toast.LENGTH_SHORT).show();
                return true;
            default:
                //Action was not recognized. Kick it upstairs for the superclass to handle.
                return super.onOptionsItemSelected(item);
        }
    }
}
