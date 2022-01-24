package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.UUID;

public class AddEditMovie extends AppCompatActivity {

    private Button confirmButton, cancelButton;
    EditText editMovieTitle, editMovieLength, editMovieAgeRating;
    List<Movie> movieList;
    MovieStorage movieStorage = MovieStorage.getInstance();
    int id;
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

        Intent newIntent = getIntent();
        id = newIntent.getIntExtra("id",-1); //jesli -1 to tryb dodawania nowego filmu

        if(id >= 0){
            Movie addMovie = null;
            for (Movie movie: movieList) {
                if(movie.getId().equals(id)){
                    addMovie = movie;
                }
            }
            editMovieTitle.setText(addMovie.getTitle());
            editMovieLength.setText(addMovie.getLength());
            editMovieAgeRating.setText(addMovie.getAge());

        }
        else {

        }
        confirmButton.setOnClickListener(v -> {

            if(id >= 0){
                Movie editedMovie = new Movie(editMovieTitle.getText().toString(),
                        Integer.parseInt(editMovieLength.getText().toString()),
                        Integer.parseInt(editMovieAgeRating.getText().toString()));
                UUID uniqueid;
                uniqueid = id;
                movieStorage.setMovie(uniqueid,editedMovie);
            }
            else  {
                Movie newMovie = new Movie(editMovieTitle.getText().toString(),
                        Integer.parseInt(editMovieLength.getText().toString()),
                        Integer.parseInt(editMovieAgeRating.getText().toString()));
                movieStorage.addMovie(newMovie);
            }

            Intent intent = new Intent(AddEditMovie.this,MainActivity.class);
            startActivity(intent);
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditMovie.this,MainActivity.class);
            startActivity(intent);
        });

    }
}