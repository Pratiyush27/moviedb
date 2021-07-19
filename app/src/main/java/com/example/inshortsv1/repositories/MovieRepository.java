package com.example.inshortsv1.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inshortsv1.models.Movie;
import com.example.inshortsv1.requests.ApiClient;

import java.util.List;

public class MovieRepository {

    private ApiClient apiClient;

    private static MovieRepository instance;

    private String Query;
    private int PageNumber;

    public static MovieRepository getInstance()
    {
        if(instance == null)
        {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository()
    {
        apiClient = ApiClient.getInstance();
    }

    public LiveData<List<Movie>> getMovies()
    {
        return apiClient.getMovie();
    }
    public LiveData<List<Movie>> getPopularMovies()
    {
        return apiClient.getPopularMovie();
    }

    public void searchMovieApi(String query, int pagenumber)
    {
        Query = query;
        PageNumber = pagenumber;
        apiClient.searchMoviesApi(query, pagenumber);

    }

    public void searchPopularMovieApi(int pagenumber)
    {
        PageNumber = pagenumber;
        apiClient.searchPopularMoviesApi(pagenumber);

    }

    public void searchFurtherPages()
    {
        searchMovieApi(Query,PageNumber+1);
    }

    public void searchNowshowingMovieApi(int pagenumber) {

        PageNumber = pagenumber;
        apiClient.searchNowshowingMoviesApi(pagenumber);
    }

    public LiveData<List<Movie>> getNowshowingrMovies() {
        return apiClient.getNowshowingMovie();
    }
}
