package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class BuyTicket extends AppCompatActivity {

    private TextView buyTicketMovieTitle, buyTicketMovieLength, buyTicketMovieAgeRating, buyTicketMovieSeatsAvailable, buyTicketMovieDate;
    private Button readReviewsButton;

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_AGE_RATING = "EXTRA_AGE_RATING";
    public static final String EXTRA_LENGTH = "EXTRA_LENGTH";
    public static final String EXTRA_SEATS_AVAILABLE = "EXTRA_SEATS_AVAILABLE";
    public static final String EXTRA_DATE = "EXTRA_DATE";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.buy_ticket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.buy_ticket:
                buyTicket();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);

        }
    }

    private void buyTicket(){

        String title = buyTicketMovieTitle.getText().toString();
        int age = Integer.parseInt(buyTicketMovieAgeRating.getText().toString());
        int length = Integer.parseInt(buyTicketMovieLength.getText().toString());
        int seats = Integer.parseInt(buyTicketMovieSeatsAvailable.getText().toString());
        String stringDate = buyTicketMovieDate.getText().toString();





        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_AGE_RATING,age);
        data.putExtra(EXTRA_LENGTH,length);
        data.putExtra(EXTRA_SEATS_AVAILABLE,seats);
        data.putExtra(EXTRA_DATE,stringDate);

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
        setContentView(R.layout.activity_buy_ticket);
        setTitle(R.string.activity_title_buy_ticket);


        buyTicketMovieTitle = findViewById(R.id.buy_ticket_movie_title);
        buyTicketMovieLength = findViewById(R.id.buy_ticket_movie_length);
        buyTicketMovieAgeRating = findViewById(R.id.buy_ticket_rating);
        buyTicketMovieSeatsAvailable = findViewById(R.id.buy_ticket_movie_seats);
        buyTicketMovieDate = findViewById(R.id.buy_ticket_movie_date);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_movie_edit);

        Intent intent = getIntent();

        buyTicketMovieTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        buyTicketMovieLength.setText(String.valueOf(intent.getIntExtra(EXTRA_LENGTH,120)));
        buyTicketMovieAgeRating.setText(String.valueOf(intent.getIntExtra(EXTRA_AGE_RATING,3)));
        buyTicketMovieSeatsAvailable.setText(String.valueOf(intent.getIntExtra(EXTRA_SEATS_AVAILABLE, 40)));
        buyTicketMovieDate.setText(intent.getStringExtra(EXTRA_DATE));

        readReviewsButton = findViewById(R.id.button_read_reviews);

        readReviewsButton.setOnClickListener(v -> {
            String movieUri = "https://www.rottentomatoes.com/m/" + intent.getStringExtra(EXTRA_TITLE);
            Intent reviewIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(movieUri));
            startActivity(reviewIntent);
        });



    }
}