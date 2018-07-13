package com.example.phoen.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.phoen.popularmovies.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

//https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Result> mMovies;

    MyRecyclerViewAdapter(Context context, List<Result> data) {
        this.mInflater=LayoutInflater.from(context);
        this.mMovies=data;
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    //get data at click position
    Result getItem(int id) {
        return mMovies.get(id);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener=itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImage;
        OutlineTextView movieTitle;

        ViewHolder(View itemView){
            super(itemView);
            posterImage= itemView.findViewById(R.id.imageViewPoster);
            movieTitle= itemView.findViewById(R.id.textViewTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=mClickListener) mClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.poster_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result temp = mMovies.get(position);
        String poster = "https://image.tmdb.org/t/p/w185" + temp.getPosterPath();
        Picasso.get().load(poster)
                .centerCrop()
                .fit()
                .placeholder(R.drawable.movie01)
                .error(R.drawable.movie01)
                .into(holder.posterImage);
        holder.movieTitle.setVisibility(MainActivity.showTitles?View.VISIBLE:View.INVISIBLE);
        holder.movieTitle.setText(temp.getTitle());
    }
}
