package com.ethanjhowell.flix.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.ethanjhowell.flix.R;
import com.ethanjhowell.flix.databinding.ActivityMovieTrailerBinding;
import com.ethanjhowell.flix.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    public static void start(Context context, Movie movie) {
        movie.setVideoID(context, () -> {
            Intent intent = new Intent(context, MovieTrailerActivity.class);
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
            context.startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieTrailerBinding binding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        // resolve the player view from the layout
        YouTubePlayerView playerView = binding.player;

        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // loads and plays the video
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                youTubePlayer.loadVideo(movie.getVideoID());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}