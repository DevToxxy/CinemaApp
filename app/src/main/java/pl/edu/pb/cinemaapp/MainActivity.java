package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;

public class MainActivity extends AppCompatActivity {

    private Button addMovieButton;
    MovieStorage movieStorage = MovieStorage.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addMovieButton = findViewById(R.id.button_add_movie);

        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditMovie.class);
                intent.putExtra("id",AddEditMovie.addingMode);
                startActivity(intent);
            }
        });

        List<Movie> movieList = movieStorage.getMovies();
        Toast.makeText(this, "movie count " + movieList.size(), Toast.LENGTH_SHORT).show(); //TODO: get rid of

        recyclerView = findViewById(R.id.movie_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MovieAdapter(movieList, MainActivity.this);
        recyclerView.setAdapter(mAdapter);


    }
    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        public List<Movie> movieList;
        Context context = MainActivity.this;
        public MovieAdapter(List<Movie> movieList, Context context) {
            this.movieList = movieList;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_movie, parent, false);
            MovieHolder holder = new MovieHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            holder.titleTextView.setText(movieList.get(position).getTitle());
            holder.ageTextView.setText(String.valueOf(movieList.get(position).getAge()));
            holder.lengthTextView.setText(String.valueOf(movieList.get(position).getLength()));
            Glide.with(this.context).load("https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg").into(holder.pictureImageView);
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddEditMovie.class);
                    UUID tempID = movieList.get(holder.getAdapterPosition()).getId(); //position wyrzuca blad: do not treat position as fixed

                    intent.putExtra("id",tempID.toString());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

    }
    private class MovieHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView ageTextView;
        private TextView lengthTextView;
        private ImageView pictureImageView;

        ConstraintLayout parentLayout;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.one_movie_title);
            ageTextView = itemView.findViewById(R.id.one_movie_age_rating);
            lengthTextView = itemView.findViewById(R.id.one_movie_length);

            pictureImageView = itemView.findViewById(R.id.one_movie_picture);

            parentLayout = itemView.findViewById(R.id.one_movie_layout);

        }

    }
}