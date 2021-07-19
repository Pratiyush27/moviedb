package com.example.inshortsv1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.inshortsv1.models.Movie;

public class IndividualMovieDetails extends AppCompatActivity {

    //widgets
    private ImageView imageViewDetails;
    private TextView titleDetails,descDetails;
    private RatingBar ratingBarDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageViewDetails = findViewById(R.id.imageView_detail);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textview_Description);
        ratingBarDetails = findViewById(R.id.ratingBar_detail);


        GetDataFromIntent();


    }

    private void GetDataFromIntent() {


        if(getIntent().hasExtra("movie")){
            Movie movie = getIntent().getParcelableExtra("movie");
            Log.v("Tag","incoming Intent"+movie.getId());

            titleDetails.setText(movie.getTitle());
            descDetails.setText(movie.getOverview());
            ratingBarDetails.setRating(movie.getVote_average()/2);

            Log.v("Tagy","X"+movie.getOverview());


            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"
                            +movie.getPoster_path())
                    .into(imageViewDetails);

        }

    }


}
