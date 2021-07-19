package com.example.inshortsv1.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import com.example.inshortsv1.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Popular_view_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageViewPop;
    RatingBar ratingBarPop;

    OnMovieListener onMovieListnerPop;

    public Popular_view_Holder(@NonNull View itemView, OnMovieListener onMovieListnerPop) {
        super(itemView);
        this.onMovieListnerPop = onMovieListnerPop;


        imageViewPop = itemView.findViewById(R.id.movie_imgPop);
        ratingBarPop = itemView.findViewById(R.id.ratingBarPop);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        onMovieListnerPop.onMovieClick(getAbsoluteAdapterPosition());

    }
}
