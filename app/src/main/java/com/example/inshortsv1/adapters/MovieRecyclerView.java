package com.example.inshortsv1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inshortsv1.R;
import com.example.inshortsv1.models.Movie;
import com.example.inshortsv1.utils.Credential;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Movie> mutablemovies;
    private OnMovieListener onMovieListener;
    private MovieViewHolder movieViewHolder;
    private static final int DISPLAY_POP =1;
    private static final int DISPLAY_SEARCH =2;



    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,parent,false);
            return new MovieViewHolder(view,onMovieListener);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout,
                    parent,false);
            return new Popular_view_Holder(view,onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int itemViewType = getItemViewType(i);

        if(itemViewType == DISPLAY_SEARCH){
            ((MovieViewHolder) holder).ratingBar.setRating(mutablemovies.get(i).getVote_average()/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+mutablemovies.get(i).getPoster_path())
                    .into(((MovieViewHolder) holder).imageView);

        }
        else {

            ((Popular_view_Holder) holder).ratingBarPop.setRating(mutablemovies.get(i).getVote_average()/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+mutablemovies.get(i).getPoster_path())
                    .into(((Popular_view_Holder) holder).imageViewPop);

        }
    }

    @Override
    public int getItemCount() {
        if(mutablemovies == null)
            return 0;
        return mutablemovies.size();
    }

    public void setMutablemovies(List<Movie> mutablemovies)
    {
        this.mutablemovies = mutablemovies;
        notifyDataSetChanged();
    }

    public Movie getMoviebyID(int posn)
    {
        if(mutablemovies != null)
        {
            if(mutablemovies.size()>0)
            {
                return mutablemovies.get(posn);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position){
        if(Credential.POPULAR){
            return DISPLAY_POP;
        }
        else
            return DISPLAY_SEARCH;
    }
}
