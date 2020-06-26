package com.ethanjhowell.flix.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@Parcel
public class Movie {
    private static final String TAG = Movie.class.getCanonicalName();

    String posterPath;
    String backdropPath;
    String title;
    String overview;
    String videoID;
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

    public String getMovieEndpointURL() {
        return String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
    }

    public void getVideoID(Context context, StringRunnable callback) {
        if (videoID == null) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(this.getMovieEndpointURL(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        Log.i(TAG, "onSuccess: Results " + results.toString());
                        JSONObject firstVideo = results.getJSONObject(0);
                        videoID = firstVideo.getString("key");
                        callback.run(videoID);
                    } catch (JSONException e) {
                        onFailure(0, null, null, e);
                    }
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "onSuccess: Failure getting results from response", throwable);
                    Toast.makeText(context, "Unable to load the video", Toast.LENGTH_SHORT).show();
                }
            });
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
