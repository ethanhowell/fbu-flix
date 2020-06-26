package com.ethanjhowell.flix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ethanjhowell.flix.databinding.ActivityMovieDetailsBinding;
import com.ethanjhowell.flix.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    private final static String TAG = MovieDetailsActivity.class.getCanonicalName();
    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        RelativeLayout root = binding.getRoot();
        setContentView(root);

        // unwrap the movie from the parcel
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        assert movie != null;
        Log.d(TAG, String.format("Showing details for \"%s\"", movie.getTitle()));

        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;

        // TODO -- display image instead and set the onclick listener to that
        tvTitle.setOnClickListener((View view) -> {
            startActivity(new Intent(this, MovieTrailerActivity.class));
        });

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        // vote average is from 0-1.0, multiply by num stars
        rbVoteAverage.setRating((float) (movie.getVoteAverage() * rbVoteAverage.getNumStars()));
    }
}