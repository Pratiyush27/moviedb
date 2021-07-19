package com.example.inshortsv1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String poster_path;
    private String release_date;
    private int movie_id;
    private float vote_average;
    private String overview;


    public Movie(String title, String poster_path, String release_date, int id, float vote_average, String overview) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.movie_id = id;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        movie_id = in.readInt();
        vote_average = in.readFloat();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return movie_id;
    }

    public void setId(int id) {
        this.movie_id = id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeInt(movie_id);
        dest.writeFloat(vote_average);
        dest.writeString(overview);
    }
}
