package com.example.inshortsv1.responses;

import com.example.inshortsv1.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    private int total_count;

    @SerializedName("results")
    @Expose
    private List<Movie> movieList;

    public int getTotal_count()
    {
        return total_count;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movieList=" + movieList +
                '}';
    }


}
