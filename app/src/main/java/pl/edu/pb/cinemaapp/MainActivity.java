package pl.edu.pb.cinemaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.NonNull;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_MOVIE_REQUEST = 1;
    private MovieViewModel movieViewModel;

    private FloatingActionButton addMovieButton;
    //MovieStorage movieStorage = MovieStorage.getInstance(); //TODO:

    //private RecyclerView recyclerView; //TODO
    //private RecyclerView.Adapter movieAdapter; //TODO
    //private RecyclerView.LayoutManager layoutManager; //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.movie_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MovieAdapter movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });

        addMovieButton = findViewById(R.id.button_add_movie);

        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditMovie.class);
                //intent.putExtra(AddEditMovie.extraId,AddEditMovie.addingMode);
                startActivityForResult(intent,ADD_MOVIE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@androidx.annotation.NonNull RecyclerView recyclerView,
                                  @androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder,
                                  @androidx.annotation.NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                movieViewModel.delete(movieAdapter.getMovieAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //List<Movie> movieList = movieStorage.getMovies(); //TODO:
        //Toast.makeText(this, "movie count " + movieList.size(), Toast.LENGTH_SHORT).show(); //TODO: get rid of

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MOVIE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditMovie.EXTRA_TITLE);
            int ageRating = data.getIntExtra(AddEditMovie.EXTRA_AGE_RATING,3);
            int length = data.getIntExtra(AddEditMovie.EXTRA_LENGTH,120);

            Movie movie = new Movie(title,ageRating,length);
            movieViewModel.insert(movie);

            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Saving aborted", Toast.LENGTH_SHORT).show();

        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        //public List<Movie> movieList; //TODO:
        private List<Movie> movies = new ArrayList<>();

        Context context = MainActivity.this;

//        public MovieAdapter(List<Movie> movieList, Context context) { //TODO:
//            this.movieList = movieList;
//        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.one_movie, parent, false);

            MovieHolder holder = new MovieHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie currentMovie = movies.get(position);

            holder.titleTextView.setText(currentMovie.getTitle());
            holder.ageTextView.setText(String.valueOf(currentMovie.getAge()));
            holder.lengthTextView.setText(String.valueOf(currentMovie.getLength()));
            Glide.with(this.context)
                    .load("https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg")
                    .into(holder.pictureImageView);

//            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, AddEditMovie.class);
//                    UUID tempID = movieList.get(holder.getAdapterPosition()).getId(); //position wyrzuca blad: do not treat position as fixed
//
//                    intent.putExtra(AddEditMovie.extraId,tempID.toString());
//                    context.startActivity(intent);
//                }
//            });  //TODO:
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        private void setMovies(List<Movie> movies){
            this.movies = movies;
            notifyDataSetChanged();
        }

        private Movie getMovieAt(int position){
            return movies.get(position);
        }


    }
    private class MovieHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView ageTextView;
        private TextView lengthTextView;
        private ImageView pictureImageView;

        // ConstraintLayout parentLayout; //TODO:

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.one_movie_title);
            ageTextView = itemView.findViewById(R.id.one_movie_age_rating);
            lengthTextView = itemView.findViewById(R.id.one_movie_length);

            pictureImageView = itemView.findViewById(R.id.one_movie_picture);

            // parentLayout = itemView.findViewById(R.id.one_movie_layout); //TODO:

        }

    }
}