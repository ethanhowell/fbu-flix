package com.ethanjhowell.flix.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ethanjhowell.flix.R;
import com.ethanjhowell.flix.activities.MovieDetailsActivity;
import com.ethanjhowell.flix.databinding.ItemMovieBinding;
import com.ethanjhowell.flix.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final static String TAG = MovieAdapter.class.getCanonicalName();
    Context context;
    List<Movie> movies;
    ItemMovieBinding binding;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        ImageView ivPoster;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = binding.tvTitle;
            ivPoster = binding.ivPoster;
            tvOverview = binding.tvOverview;
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageurl;
            int placeholder;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageurl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
                Log.d(TAG, "bind: landscape");
            } else {
                imageurl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }

            Glide.with(context)
                    .load(imageurl)
                    .placeholder(placeholder)
                    .transform(new RoundedCorners(10))
                    .into(ivPoster);
        }

        @Override
        public void onClick(View view) {
            // gets position of clicked on movie
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(pos);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(movie.getClass().getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
