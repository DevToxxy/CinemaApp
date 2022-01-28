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
import android.widget.AdapterView;
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
    public static final int EDIT_MOVIE_REQUEST = 2;
    private MovieViewModel movieViewModel;

    private FloatingActionButton addMovieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.movie_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this);
        recyclerView.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.submitList(movies);
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

        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(MainActivity.this, AddEditMovie.class);

                intent.putExtra(AddEditMovie.extraId,movie.getId());
                intent.putExtra(AddEditMovie.EXTRA_TITLE, movie.getTitle());
                intent.putExtra(AddEditMovie.EXTRA_LENGTH, movie.getLength());
                intent.putExtra(AddEditMovie.EXTRA_AGE_RATING, movie.getAge());
                startActivityForResult(intent, EDIT_MOVIE_REQUEST);
            }
        });



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
        else if (requestCode == EDIT_MOVIE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditMovie.extraId, -1);
            if (id == -1) {
                Toast.makeText(this, "Can't update", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditMovie.EXTRA_TITLE);
            int ageRating = data.getIntExtra(AddEditMovie.EXTRA_AGE_RATING,3);
            int length = data.getIntExtra(AddEditMovie.EXTRA_LENGTH,120);

            Movie movie = new Movie(title,ageRating,length);
            movie.setId(id);
            movieViewModel.update(movie);

            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Saving aborted", Toast.LENGTH_SHORT).show();

        }
    }



}