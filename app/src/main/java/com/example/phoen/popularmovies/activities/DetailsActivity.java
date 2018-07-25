package com.example.phoen.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.example.phoen.popularmovies.R;
import com.example.phoen.popularmovies.adapters.ReviewViewAdapter;
import com.example.phoen.popularmovies.models.ReviewResult;
import com.example.phoen.popularmovies.models.Reviews;
import com.example.phoen.popularmovies.models.TMDBResult;
import com.example.phoen.popularmovies.rest.ApiClient;
import com.example.phoen.popularmovies.rest.ApiInterface;
import com.example.phoen.popularmovies.utils.OutlineTextView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//https://material.io/develop/android/components/collapsing-toolbar-layout/
//http://blog.grafixartist.com/toolbar-animation-with-android-design-support-library/
//http://yaronvazana.com/2015/12/28/material-design-collapsing-toolbar-with-image/
public class DetailsActivity extends AppCompatActivity {

    private RecyclerView reviewRecyclerView;
    private ReviewViewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        reviewRecyclerView = findViewById(R.id.Reviews);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra("MovieObject")) {
            TMDBResult movie = Objects.requireNonNull(parentIntent.getExtras()).getParcelable("MovieObject");

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Reviews> call;
            call = apiService.getMovieReviews(movie.getId());

            call.enqueue(new Callback<Reviews>() {
                @Override
                public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                    List<ReviewResult> mReviews = Objects.requireNonNull(response.body()).getResults();
                    Log.d("Details","Reviews received: " + mReviews.size());
                    reviewAdapter = new ReviewViewAdapter(DetailsActivity.this,mReviews);
                    reviewRecyclerView.setAdapter(reviewAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
                    Log.e("Details",t.toString());
                }
            });

            final OutlineTextView originalTitle = findViewById(R.id.OriginalTitle);
            originalTitle.setText(Objects.requireNonNull(movie).getOriginalTitle());

            final OutlineTextView rating = findViewById(R.id.Rating);
            rating.setText(String.format(Locale.US,"%.1f", movie.getVoteAverage()));

            final OutlineTextView releaseDate = findViewById(R.id.ReleaseDate);
            releaseDate.setText(movie.getReleaseDate());

            final OutlineTextView overview = findViewById(R.id.Overview);
            overview.setText(movie.getOverview());

            final ImageView poster = findViewById(R.id.Poster);
            String posterPath = "https://image.tmdb.org/t/p/w185" + movie.getPosterPath();
            Picasso.get().load(posterPath)
                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.movie01)
                    .error(R.drawable.movie01)
                    .into(poster);

            final Toolbar toolbar = findViewById(R.id.detail_toolbar);
            setSupportActionBar(toolbar);
            if (null!=getSupportActionBar()) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final AppBarLayout appBarLayout = findViewById(R.id.detail_appBar);

            final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.detail_collapsing_toolbar);
            collapsingToolbar.setTitle(movie.getTitle());


            //int imageDimension = (int) getResources().getDimension(R.dimen.image_size);
            final ImageView header = findViewById(R.id.detail_header);
            String headerPath = "https://image.tmdb.org/t/p/w300" + movie.getBackdropPath();
            Picasso.get().load(headerPath)
                    .centerCrop()
                    .fit()
                    .into(header);


            //Todo: Generate palettes based on loaded image
//            Picasso.get().load(headerPath)
//                    .resize(imageDimension,imageDimension)
//                    .centerCrop()
//                    //.fit()
//                    .into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                            assert header!=null;
//                            header.setImageBitmap(bitmap);
//                            Palette.from(bitmap)
//                                    .generate(new Palette.PaletteAsyncListener() {
//                                        @Override
//                                        public void onGenerated(@NonNull Palette palette) {
//                                            Palette.Swatch textSwatch = palette.getVibrantSwatch();
//                                            if (textSwatch == null) {
//                                                Toast.makeText(Details.this,"Null swatch :(", Toast.LENGTH_SHORT).show();
//                                                return;
//                                            }
//                                            collapsingToolbar.setContentScrimColor(textSwatch.getBodyTextColor());
//                                            collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.cardview_dark_background));
//
//                                        }
//                                    });
//
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });



            }
    }

}
