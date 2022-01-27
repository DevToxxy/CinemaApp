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
    private EditText editMovieTitle, editMovieLength, editMovieAgeRating;
    private List<Movie> movieList;
    private MovieStorage movieStorage = MovieStorage.getInstance();
    private String id;
    public final static String addingMode = "AddingMode";
    public final static String extraId = "id";
    boolean isAllFieldsChecked = false;
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
        id = newIntent.getExtras().getString(extraId,addingMode);
        if(!(id.equals(addingMode))){  //Editing mode
            UUID temporaryId = UUID.fromString(id);
            Movie editMovie = null;
            for (Movie movie: movieList) {
                if(movie.getId().equals(temporaryId)){
                    editMovie = movie;
                    editMovieTitle.setText(editMovie.getTitle());
                    editMovieLength.setText(String.valueOf(editMovie.getLength()));
                    editMovieAgeRating.setText(String.valueOf(editMovie.getAge()));

                }
            }

        }
        else {  //Adding mode

        }
        confirmButton.setOnClickListener(v -> {
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {
                if (!(id.equals(addingMode))) {         //Editing mode
                    Movie editedMovie = new Movie(editMovieTitle.getText().toString(),
                            Integer.parseInt(editMovieLength.getText().toString()),
                            Integer.parseInt(editMovieAgeRating.getText().toString()));
                    UUID temporaryId = UUID.fromString(id);

                    movieStorage.setMovie(temporaryId, editedMovie);
                } else {                 //Adding mode
                    Movie newMovie = new Movie(editMovieTitle.getText().toString(),
                            Integer.parseInt(editMovieLength.getText().toString()),
                            Integer.parseInt(editMovieAgeRating.getText().toString()));
                    movieStorage.addMovie(newMovie);
                }

                Intent intent = new Intent(AddEditMovie.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditMovie.this,MainActivity.class);
            startActivity(intent);
        });

    }
    private boolean CheckAllFields() {
        if (editMovieTitle.length() == 0) {
            editMovieTitle.setError("Title  is required");
            return false;
        }
        if (editMovieAgeRating.length() == 0) {
            editMovieAgeRating.setError("Rating is required");
            return false;
        }
        else if (!(editMovieAgeRating.getText().toString().matches("^[0-9]{1,2}$"))) {
            editMovieAgeRating.setError("Only numbers allowed. Max number:99");
            return false;
        }

        if (editMovieLength.length() == 0) {
            editMovieLength.setError("Length is required");
            return false;
        }
        else if (!(editMovieLength.getText().toString().matches("^[0-9]{1,3}$"))) {
            editMovieLength.setError("Only numbers allowed. Max number:999");
            return false;
        }


        // after all validation return true.
        return true;
    }
}