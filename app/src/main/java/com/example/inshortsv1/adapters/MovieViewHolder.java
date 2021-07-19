package com.example.inshortsv1.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inshortsv1.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    TextView title,release_date;
    ImageView imageView;
    RatingBar ratingBar;

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getRelease_date() {
        return release_date;
    }

    public void setRelease_date(TextView release_date) {
        this.release_date = release_date;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public OnMovieListener getOnMovieListener() {
        return onMovieListener;
    }

    public void setOnMovieListener(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;
        title = itemView.findViewById(R.id.movie_title);
        release_date = itemView.findViewById(R.id.movie_category);
        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onCategoryClick(getAdapterPosition());
    }
}
