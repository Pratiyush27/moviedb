package com.example.inshortsv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.inshortsv1.adapters.MovieRecyclerView;
import com.example.inshortsv1.adapters.OnMovieListener;
import com.example.inshortsv1.models.Movie;
import com.example.inshortsv1.requests.Services;
import com.example.inshortsv1.responses.MovieSearchResponse;
import com.example.inshortsv1.utils.Api;
import com.example.inshortsv1.utils.Credential;
import com.example.inshortsv1.viewmodels.MovieListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import androidx.appcompat.widget.Toolbar;
import static android.widget.Toast.LENGTH_SHORT;

public class MoviesActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView,recyclerView2;
    private MovieRecyclerView movieRecyclerViewAdapter;
    private MovieListView movieListView;
    private boolean ispopular = true,nowshowing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupSearch();


        recyclerView = findViewById(R.id.recyclerViewpopular);
        recyclerView2 = findViewById(R.id.recyclerViewnowplaying);
        movieListView = new ViewModelProvider(this).get(MovieListView.class);

        movieListView.searchPopularMovieApi(1);
        movieListView.searchNowshowingMovieApi(1);
        ConfigureRecycler();
        ObserveChange();
        ObservePopularMovies();
    }

    private void ObservePopularMovies() {
        movieListView.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies != null)
                {
                    for(Movie movie : movies)
                    {
                        Log.v("Tag","Title is " + movie.getTitle());

                        movieRecyclerViewAdapter.setMutablemovies(movies);

                    }
                }
            }
        });
    }

    private void setupSearch() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListView.searchMovieApi(query,1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ispopular = false; nowshowing = false;
                TextView textView1 = findViewById(R.id.Popular);
                TextView textView2 = findViewById(R.id.Nowshowing);
                RecyclerView recyclerView1 = findViewById(R.id.recyclerViewpopular);

                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.GONE);
            }
        });
    }


    private void ObserveChange()
    {
        movieListView.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies != null)
                {
                    for(Movie movie : movies)
                    {
                        Log.v("Tag","Title is " + movie.getTitle());

                        movieRecyclerViewAdapter.setMutablemovies(movies);

                    }
                }
            }
        });
    }


    private void GetRetrofitResponse() {
        Api api = Services.getApi();
        Call<MovieSearchResponse> responseCall = api.searchMovie(
                Credential.api_key,"Action","1");
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200)
                {
                    assert response.body() != null;
                    Log.v("Tag","The Response is "+response.body().toString());
                    List<Movie> movieList = new ArrayList<>(response.body().getMovieList());
                    for(Movie movie : movieList)
                    {
                        Log.v("Tag","The Release Date is "+movie.getRelease_date());
                    }
                }
                else
                {
                    try{
                        assert response.errorBody() != null;
                        Log.v("Tag","Error "+ response.errorBody().toString());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private void searchMovieApi(String query,int pagenumber)
    {
        movieListView.searchMovieApi(query, pagenumber);
    }




    private void GetRetrofitReesponseBasedonID()
    {
        Api api = Services.getApi();
        Call<Movie> responseCall = api.getMovie(
                550,Credential.api_key);

        responseCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.code() ==200)
                {
                    Movie movie = response.body();
                    Log.v("Tag","The Response " + movie.getTitle());
                }
                else
                {
                    try
                    {
                        Log.v("Tag","Error "+ response.errorBody().toString());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void ConfigureRecycler()
    {
        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(recyclerView.canScrollVertically(1)){
                    //here we need to diaplay  the next search result in the next page of api
                    movieListView.getNextPage();
                }
            }
        });
        recyclerView2.setAdapter(movieRecyclerViewAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(recyclerView.canScrollVertically(1)){
                    //here we need to diaplay  the next search result in the next page of api
                    movieListView.getNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int posn) {
        Intent intent = new Intent(this,IndividualMovieDetails.class);
        intent.putExtra("movie",movieRecyclerViewAdapter.getMoviebyID(posn));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(int category) {

    }


}