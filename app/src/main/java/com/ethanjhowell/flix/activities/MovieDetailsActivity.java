package com.ethanjhowell.flix.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ethanjhowell.flix.R;
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

        binding.ivPoster.setOnClickListener((View view) -> {
            MovieTrailerActivity.start(this, movie);
        });

        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .transform(new RoundedCorners(10))
                .into(binding.ivPoster);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        // vote average is from 0-1.0, multiply by num stars
        rbVoteAverage.setRating((float) (movie.getVoteAverage() * rbVoteAverage.getNumStars()));
    }
}