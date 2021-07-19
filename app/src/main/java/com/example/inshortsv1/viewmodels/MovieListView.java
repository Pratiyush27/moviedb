package com.example.inshortsv1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inshortsv1.models.Movie;
import com.example.inshortsv1.repositories.MovieRepository;

import java.util.List;

public class MovieListView extends ViewModel {


    private MovieRepository movieRepository;

    public MovieListView() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Movie>> getMovies()
    {
        return movieRepository.getMovies();
    }
    public LiveData<List<Movie>> getPopularMovies()
    {
        return movieRepository.getPopularMovies();
    }
    public LiveData<List<Movie>> getNowshowingMovies()
    {
        return movieRepository.getNowshowingrMovies();
    }

    public void searchMovieApi(String query,int pagenumber)
    {
        movieRepository.searchMovieApi(query, pagenumber);
    }

    public void searchPopularMovieApi(int pagenumber)
    {
        movieRepository.searchPopularMovieApi(pagenumber);
    }

    public void searchNowshowingMovieApi(int pagenumber)
    {
        movieRepository.searchNowshowingMovieApi(pagenumber);
    }

    public void getNextPage()
    {
        movieRepository.searchFurtherPages();
    }

}
