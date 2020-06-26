package com.ethanjhowell.flix.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    // FIXME: avoid hardcoding here
    String videoID = "x8DKg_fsacM";
    int id;

    private Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        id = jsonObject.getInt("id");
        // vote average range is from 0 to 10
        voteAverage = jsonObject.getDouble("vote_average") / 10;
    }

    double voteAverage;

    public void getVideoID(StringRunnable callback) {
        if (videoID == null) {
            // TODO: api call
        } else
            callback.run(videoID);
    }

    // required for Parceler
    public Movie() {
        // purposeful empty body
    }

    public interface StringRunnable {
        void run(String s);
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public static List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
