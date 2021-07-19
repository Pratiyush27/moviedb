package com.example.inshortsv1.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inshortsv1.AppController;
import com.example.inshortsv1.models.Movie;
import com.example.inshortsv1.responses.MovieSearchResponse;
import com.example.inshortsv1.utils.Credential;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ApiClient {

    private MutableLiveData<List<Movie>> mutablemovies;
    private MutableLiveData<List<Movie>> popular_mutablemovies;
    private MutableLiveData<List<Movie>> nowshowing_mutablemovies;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    private Popular_RetrieveMoviesRunnable popular_retrieveMoviesRunnable;
    private Nowshowing_RetrieveMoviesRunnable nowshowing_retrieveMoviesRunnable;
    private static ApiClient instance;

    public static ApiClient getInstance()
    {
        if(instance == null)
        {
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient()
    {
        mutablemovies = new MutableLiveData<>();
        popular_mutablemovies = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getMovie() {
        return mutablemovies;
    }

    public LiveData<List<Movie>> getPopularMovie() {
        return popular_mutablemovies;
    }

    public void searchMoviesApi(String query, int pagenumber)
    {
        if(retrieveMoviesRunnable != null)
        {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query,pagenumber);
        final Future myHandler = AppController.getInstance().getNetworkhandler().submit(retrieveMoviesRunnable);

        AppController.getInstance().getNetworkhandler().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 10000, TimeUnit.MILLISECONDS);


    }

    public void searchPopularMoviesApi(int pagenumber)
    {
        if(popular_retrieveMoviesRunnable!= null)
        {
            popular_retrieveMoviesRunnable= null;
        }

        popular_retrieveMoviesRunnable = new Popular_RetrieveMoviesRunnable(pagenumber);
        final Future myHandler2 = AppController.getInstance().getNetworkhandler().submit(popular_retrieveMoviesRunnable);

        AppController.getInstance().getNetworkhandler().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }

    public void searchNowshowingMoviesApi(int pagenumber) {
        if(nowshowing_retrieveMoviesRunnable!= null)
        {
            nowshowing_retrieveMoviesRunnable= null;
        }

        nowshowing_retrieveMoviesRunnable = new Nowshowing_RetrieveMoviesRunnable( pagenumber);
        final Future myHandler3 = AppController.getInstance().getNetworkhandler().submit(nowshowing_retrieveMoviesRunnable);

        AppController.getInstance().getNetworkhandler().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler3.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public LiveData<List<Movie>> getNowshowingMovie() {
        return nowshowing_mutablemovies;
    }

    private class RetrieveMoviesRunnable implements  Runnable
    {

        private String query;
        private int pagenumber;
        boolean cancelreq;

        public RetrieveMoviesRunnable(String query, int pagenumber) {
            this.query = query;
            this.pagenumber = pagenumber;
            this.cancelreq = false;
        }

        @Override
        public void run() {
            try
            {
                Response response = getMovies(query, pagenumber).execute();

                if (cancelreq) {
                    return;
                }
                if(response.code() ==200)
                {
                    List<Movie> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovieList());
                    if(pagenumber==1)
                    {
                        mutablemovies.postValue(list);
                    }
                    else
                    {
                        List<Movie> currentMovie = mutablemovies.getValue();
                        currentMovie.addAll(list);
                        mutablemovies.postValue(currentMovie);
                    }
                }
                else
                {
                    Log.v("Tag","Error "+response.errorBody().string());
                    mutablemovies.postValue(null);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pagenumber)
        {
            return Services.getApi().searchMovie(Credential.api_key,query,String.valueOf(pagenumber));
        }

        private void cancelRequest()
        {
            Log.v("Tag","Search Request Cancelled");
            cancelreq = true;
        }

    }

    private class Popular_RetrieveMoviesRunnable implements  Runnable
    {

        private int pagenumber;
        boolean cancelreq;

        public Popular_RetrieveMoviesRunnable( int pagenumber) {
            this.pagenumber = pagenumber;
            this.cancelreq = false;
        }

        @Override
        public void run() {
            try
            {
                Response response2 = getPopularMovies( pagenumber).execute();

                if (cancelreq) {
                    return;
                }
                if(response2.code() ==200)
                {
                    List<Movie> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovieList());
                    if(pagenumber==1)
                    {
                        popular_mutablemovies.postValue(list);
                    }
                    else
                    {
                        List<Movie> currentMovie = popular_mutablemovies.getValue();
                        currentMovie.addAll(list);
                        popular_mutablemovies.postValue(currentMovie);
                    }
                }
                else
                {
                    Log.v("Tag","Error "+response2.errorBody().string());
                    popular_mutablemovies.postValue(null);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private Call<MovieSearchResponse> getPopularMovies(int pagenumber)
        {
            return Services.getApi().getPopular(Credential.api_key,String.valueOf(pagenumber));
        }

        private void cancelRequest()
        {
            Log.v("Tag","Search Request Cancelled");
            cancelreq = true;
        }

    }

    private class Nowshowing_RetrieveMoviesRunnable implements  Runnable
    {

        private int pagenumber;
        boolean cancelreq;

        public Nowshowing_RetrieveMoviesRunnable( int pagenumber) {
            this.pagenumber = pagenumber;
            this.cancelreq = false;
        }

        @Override
        public void run() {
            try
            {
                Response response3 = getPopularMovies( pagenumber).execute();

                if (cancelreq) {
                    return;
                }
                if(response3.code() ==200)
                {
                    List<Movie> list = new ArrayList<>(((MovieSearchResponse)response3.body()).getMovieList());
                    if(pagenumber==1)
                    {
                        nowshowing_mutablemovies.postValue(list);
                    }
                    else
                    {
                        List<Movie> currentMovie = nowshowing_mutablemovies.getValue();
                        currentMovie.addAll(list);
                        nowshowing_mutablemovies.postValue(currentMovie);
                    }
                }
                else
                {
                    Log.v("Tag","Error "+response3.errorBody().string());
                    nowshowing_mutablemovies.postValue(null);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private Call<MovieSearchResponse> getPopularMovies(int pagenumber)
        {
            return Services.getApi().getNowshowing(Credential.api_key,String.valueOf(pagenumber));
        }

        private void cancelRequest()
        {
            Log.v("Tag","Search Request Cancelled");
            cancelreq = true;
        }

    }
}
