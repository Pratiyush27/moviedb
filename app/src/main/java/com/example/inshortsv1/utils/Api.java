package com.example.inshortsv1.utils;

import com.example.inshortsv1.models.Movie;
import com.example.inshortsv1.responses.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") String page
    );



    @GET("/3/search/{movie_id}?")
    Call<Movie> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );

    @GET("/3/movie/now_playing")
    Call<MovieSearchResponse> getNowshowing(
            @Query("api_key") String key,
            @Query("page") String page
    );
}
