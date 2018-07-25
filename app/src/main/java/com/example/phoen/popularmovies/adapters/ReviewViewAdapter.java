package com.example.phoen.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoen.popularmovies.R;
import com.example.phoen.popularmovies.models.ReviewResult;
import com.example.phoen.popularmovies.utils.OutlineTextView;

import java.util.List;

public class ReviewViewAdapter extends RecyclerView.Adapter<ReviewViewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<ReviewResult> mReviews;

    public ReviewViewAdapter(Context context, List<ReviewResult> data) {
        this.mInflater=LayoutInflater.from(context);
        this.mReviews=data;
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OutlineTextView reviewAuthor;
        OutlineTextView reviewContent;

        ViewHolder(View itemView){
            super(itemView);
            reviewAuthor= itemView.findViewById(R.id.ReviewAuthor);
            reviewContent= itemView.findViewById(R.id.ReviewContent);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewResult temp = mReviews.get(position);
        holder.reviewAuthor.setText("Review by " + temp.getAuthor() + ":");
        holder.reviewContent.setText(temp.getContent());
    }
}
