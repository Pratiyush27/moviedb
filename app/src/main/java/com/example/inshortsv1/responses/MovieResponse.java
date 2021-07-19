package com.example.inshortsv1.responses;

import com.example.inshortsv1.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

}
