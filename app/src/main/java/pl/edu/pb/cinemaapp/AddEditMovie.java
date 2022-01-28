package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.UUID;

public class AddEditMovie extends AppCompatActivity {

    //private Button confirmButton, cancelButton; //TODO:
    private EditText editMovieTitle, editMovieLength, editMovieAgeRating;
    //private List<Movie> movieList;
    //private MovieStorage movieStorage = MovieStorage.getInstance(); //TODO:
    private String id;

    public static final String addingMode = "AddingMode";
    public static final String extraId = "id";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_AGE_RATING = "EXTRA_AGE_RATING";
    public static final String EXTRA_LENGTH = "EXTRA_LENGTH";


    boolean isAllFieldsChecked = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.save_movie:
                saveMovie();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);

        }
    }
    private void saveMovie(){
        isAllFieldsChecked = CheckAllFields();

        if (!isAllFieldsChecked) {
            return;
        }
        String title = editMovieTitle.getText().toString();
        int age = Integer.parseInt(editMovieAgeRating.getText().toString());
        int length = Integer.parseInt(editMovieLength.getText().toString());



        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_AGE_RATING,age);
        data.putExtra(EXTRA_LENGTH,length);

        setResult(RESULT_OK, data);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        //movieList = movieStorage.getMovies(); //TODO:

        //confirmButton = findViewById(R.id.button_confirm);//TODO:
       // cancelButton = findViewById(R.id.button_cancel);//TODO:

        editMovieTitle = findViewById(R.id.edit_movie_title);
        editMovieLength = findViewById(R.id.edit_movie_length);
        editMovieAgeRating = findViewById(R.id.edit_age_rating);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_movie_edit);
        setTitle("Add movie");

//        Intent newIntent = getIntent();
//        id = newIntent.getExtras().getString(extraId, addingMode);
//        if (!(id.equals(addingMode))) {  //Editing mode
//            UUID temporaryId = UUID.fromString(id);
//            Movie editMovie = null;
//            for (Movie movie : movieList) {
//                if (movie.getId().equals(temporaryId)) {
//                    editMovie = movie;
//                    editMovieTitle.setText(editMovie.getTitle());
//                    editMovieLength.setText(String.valueOf(editMovie.getLength()));
//                    editMovieAgeRating.setText(String.valueOf(editMovie.getAge()));
//
//                }
//            }
//
//        } else {  //Adding mode
//
//        }
//        confirmButton.setOnClickListener(v -> {
//            isAllFieldsChecked = CheckAllFields();
//            if (isAllFieldsChecked) {
//                if (!(id.equals(addingMode))) {         //Editing mode
//                    Movie editedMovie = new Movie(editMovieTitle.getText().toString(),
//                            Integer.parseInt(editMovieLength.getText().toString()),
//                            Integer.parseInt(editMovieAgeRating.getText().toString()));
//                    UUID temporaryId = UUID.fromString(id);
//
//                    movieStorage.setMovie(temporaryId, editedMovie);
//                } else {                 //Adding mode
//                    Movie newMovie = new Movie(editMovieTitle.getText().toString(),
//                            Integer.parseInt(editMovieLength.getText().toString()),
//                            Integer.parseInt(editMovieAgeRating.getText().toString()));
//                    movieStorage.addMovie(newMovie);
//                }
//
//                Intent intent = new Intent(AddEditMovie.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        cancelButton.setOnClickListener(v -> {
//            Intent intent = new Intent(AddEditMovie.this, MainActivity.class);
//            startActivity(intent);
//        });

    }

    private boolean CheckAllFields() {
        if (editMovieTitle.length() == 0) {
            editMovieTitle.setError("Title  is required");
            return false;
        }
        if (editMovieAgeRating.length() == 0) {
            editMovieAgeRating.setError("Rating is required");
            return false;
        } else if (!(editMovieAgeRating.getText().toString().matches("^[0-9]{1,2}$"))) {
            editMovieAgeRating.setError("Only numbers allowed. Max number:99");
            return false;
        }

        if (editMovieLength.length() == 0) {
            editMovieLength.setError("Length is required");
            return false;
        } else if (!(editMovieLength.getText().toString().matches("^[0-9]{1,3}$"))) {
            editMovieLength.setError("Only numbers allowed. Max number:999");
            return false;
        }


        // after all validation return true.
        return true;
    }
}