package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddEditMovie extends AppCompatActivity {

    private Button confirmButton, cancelButton;
    EditText editMovieTitle, editMovieLength, editMovieAgeRating;
    List<Movie> movieList;
    MovieStorage movieStorage = MovieStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        movieList = movieStorage.getMovies();

        confirmButton = findViewById(R.id.button_confirm);
        cancelButton = findViewById(R.id.button_cancel);

        editMovieTitle = findViewById(R.id.edit_movie_title);
        editMovieLength = findViewById(R.id.edit_movie_length);
        editMovieAgeRating = findViewById(R.id.edit_age_rating);


        confirmButton.setOnClickListener(v -> {

            Movie newMovie = new Movie(editMovieTitle.getText().toString(),
                    Integer.parseInt(editMovieLength.getText().toString()),
                    Integer.parseInt(editMovieAgeRating.getText().toString()));
            movieStorage.addMovie(newMovie);
            Intent intent = new Intent(AddEditMovie.this,MainActivity.class);
            startActivity(intent);
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditMovie.this,MainActivity.class);
            startActivity(intent);
        });

    }
}