package com.example.phoen.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phoen.popularmovies.models.Result;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

//https://material.io/develop/android/components/collapsing-toolbar-layout/
//http://blog.grafixartist.com/toolbar-animation-with-android-design-support-library/
//http://yaronvazana.com/2015/12/28/material-design-collapsing-toolbar-with-image/
public class Details extends AppCompatActivity {

    Result movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra("MovieObject")) {
            movie = parentIntent.getExtras().getParcelable("MovieObject");

            //setTitle(movie.getTitle());

            final OutlineTextView originalTitle = findViewById(R.id.OriginalTitle);
            originalTitle.setText(movie.getOriginalTitle());

            final OutlineTextView rating = findViewById(R.id.Rating);
            rating.setText(String.format("%.1f",movie.getVoteAverage()));

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

            final Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
            setSupportActionBar(toolbar);
            if (null!=getSupportActionBar()) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.detail_appBar);

            final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.detail_collapsing_toolbar);
            collapsingToolbar.setTitle(movie.getTitle());


            //int imageDimension = (int) getResources().getDimension(R.dimen.image_size);
            final ImageView header = findViewById(R.id.detail_header);
            String headerPath = "https://image.tmdb.org/t/p/w300" + movie.getBackdropPath();
            Picasso.get().load(headerPath)
                    .centerCrop()
                    .fit()
                    .into(header);

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
