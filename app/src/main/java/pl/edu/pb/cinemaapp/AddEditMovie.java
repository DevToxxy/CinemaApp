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

    private EditText editMovieTitle, editMovieLength, editMovieAgeRating;

    public static final String extraId = "extraId";
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

        int id = getIntent().getIntExtra(extraId, -1);
        if (id != -1) {
            data.putExtra(extraId, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);



        editMovieTitle = findViewById(R.id.edit_movie_title);
        editMovieLength = findViewById(R.id.edit_movie_length);
        editMovieAgeRating = findViewById(R.id.edit_age_rating);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_movie_edit);

        Intent intent = getIntent();

        if (intent.hasExtra(extraId)) {
            setTitle("Edit movie");
            editMovieTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editMovieLength.setText(String.valueOf(intent.getIntExtra(EXTRA_LENGTH,120)));
            editMovieAgeRating.setText(String.valueOf(intent.getIntExtra(EXTRA_AGE_RATING,3)));
        }
        else {
            setTitle("Add movie");
        }


    }

    private boolean CheckAllFields() {
        if (editMovieTitle.length() == 0) {
            editMovieTitle.setError(getString(R.string.title_validation_req));
            return false;
        }
        if (editMovieAgeRating.length() == 0) {
            editMovieAgeRating.setError(getString(R.string.rating_validation_req));
            return false;
        } else if (!(editMovieAgeRating.getText().toString().matches("^[0-9]{1,2}$"))) {
            editMovieAgeRating.setError(getString(R.string.rating_validation_num));
            return false;
        }

        if (editMovieLength.length() == 0) {
            editMovieLength.setError(getString(R.string.length_validation_req));
            return false;
        } else if (!(editMovieLength.getText().toString().matches("^[0-9]{1,3}$"))) {
            editMovieLength.setError(getString(R.string.length_validation_num));
            return false;
        }

        return true;
    }
}