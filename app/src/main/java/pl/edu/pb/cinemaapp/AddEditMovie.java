package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import pl.edu.pb.cinemaapp.converters.CalendarConverter;


public class AddEditMovie extends AppCompatActivity {

    private EditText editMovieTitle, editMovieLength, editMovieAgeRating, editMovieSeatsAvailable, editMovieDate;

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_AGE_RATING = "EXTRA_AGE_RATING";
    public static final String EXTRA_LENGTH = "EXTRA_LENGTH";
    public static final String EXTRA_SEATS_AVAILABLE = "EXTRA_SEATS_AVAILABLE";
    public static final String EXTRA_DATE = "EXTRA_DATE";

    private boolean isAllFieldsChecked = false;
    private int year = 0, month = 0, day = 0, hour = 0, minute = 0;
    private String dateString;
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
        int seats = Integer.parseInt(editMovieSeatsAvailable.getText().toString());
        String date = editMovieDate.getText().toString();



        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_AGE_RATING,age);
        data.putExtra(EXTRA_LENGTH,length);
        data.putExtra(EXTRA_SEATS_AVAILABLE,seats);
        data.putExtra(EXTRA_DATE,date);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
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
        editMovieSeatsAvailable = findViewById(R.id.edit_movie_available_seats);
        editMovieDate = findViewById(R.id.edit_movie_date);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_movie_edit);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(R.string.activity_title_edit);
            editMovieTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editMovieLength.setText(String.valueOf(intent.getIntExtra(EXTRA_LENGTH,120)));
            editMovieAgeRating.setText(String.valueOf(intent.getIntExtra(EXTRA_AGE_RATING,3)));
            editMovieSeatsAvailable.setText(String.valueOf(intent.getIntExtra(EXTRA_SEATS_AVAILABLE, 40)));
            editMovieDate.setText(intent.getStringExtra(EXTRA_DATE));
        }
        else {
            setTitle(R.string.activity_title_add);
        }

        editMovieDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });


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

        if (editMovieSeatsAvailable.length() == 0) {
            editMovieSeatsAvailable.setError(getString(R.string.seats_validation_req));
            return false;
        } else if (!(editMovieSeatsAvailable.getText().toString().matches("^[0-9]{1,3}$"))) {
            editMovieSeatsAvailable.setError(getString(R.string.seats_validation_num));
            return false;
        }
        if (editMovieDate.length() == 0) {
            editMovieTitle.setError(getString(R.string.date_validation_req));
            return false;
        }

        return true;
    }
    private void datePicker(){
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditMovie.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        dateString = dayOfMonth  + "." + (monthOfYear + 1) + "." + year;
                        timePicker();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void timePicker(){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEditMovie.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hour = hourOfDay;
                        minute = minute;

                        editMovieDate.setText(dateString+" "+hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }


}