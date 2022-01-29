package pl.edu.pb.cinemaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import pl.edu.pb.cinemaapp.adapters.MovieAdapter;
import pl.edu.pb.cinemaapp.entities.Movie;
import pl.edu.pb.cinemaapp.entities.Ticket;
import pl.edu.pb.cinemaapp.viewmodels.MovieViewModel;
import pl.edu.pb.cinemaapp.viewmodels.TicketViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_MOVIE_REQUEST = 1;
    public static final int EDIT_MOVIE_REQUEST = 2;
    public static final int BUY_TICKET_REQUEST = 3;

    private MovieViewModel movieViewModel;
    private TicketViewModel ticketViewModel;

    private FloatingActionButton addMovieButton;
    private ConstraintLayout mainLayout;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tickets_activity:
                openTicketsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);

        }
    }

    private void openTicketsActivity(){
        Intent intent = new Intent(MainActivity.this, Tickets.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);

        createNotificationChannel();

        RecyclerView recyclerView = findViewById(R.id.movie_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this);
        recyclerView.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, movies -> movieAdapter.submitList(movies));

        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel.class);
        //ticketViewModel.getAllTickets().observe(this, tickets -> ticketAdapter.submitList(tickets));

        addMovieButton = findViewById(R.id.button_add_movie);

        addMovieButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AddEditMovie.class);
            startActivityForResult(intent,ADD_MOVIE_REQUEST);
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
                Movie movieToSave = movieAdapter.getMovieAt(viewHolder.getAdapterPosition());
                movieViewModel.delete(movieToSave);

                Snackbar.make(mainLayout, R.string.successful_deletion, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", view -> {
                            Snackbar.make(mainLayout, R.string.undo_movie_delete, Snackbar.LENGTH_SHORT).show();
                            movieViewModel.insert(movieToSave);
                        }).show();


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
                intent.putExtra(AddEditMovie.EXTRA_SEATS_AVAILABLE, movie.getSeats());

                startActivityForResult(intent, EDIT_MOVIE_REQUEST);
            }
        });

        movieAdapter.setOnLongItemClickListener(new MovieAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(Movie movie) {
                Intent intent = new Intent(MainActivity.this, BuyTicket.class);

                intent.putExtra(BuyTicket.extraId,movie.getId());
                intent.putExtra(BuyTicket.EXTRA_TITLE, movie.getTitle());
                intent.putExtra(BuyTicket.EXTRA_LENGTH, movie.getLength());
                intent.putExtra(BuyTicket.EXTRA_AGE_RATING, movie.getAge());
                intent.putExtra(BuyTicket.EXTRA_SEATS_AVAILABLE, movie.getSeats());

                startActivityForResult(intent, BUY_TICKET_REQUEST);
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
            int seats = data.getIntExtra(AddEditMovie.EXTRA_SEATS_AVAILABLE,40);

            Movie movie = new Movie(title,ageRating,length,seats);
            movieViewModel.insert(movie);

            Toast.makeText(this, R.string.successful_save, Toast.LENGTH_SHORT).show();

            //region Notification handling

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, getString(R.string.channel_id))
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(getString(R.string.notification_title))
                    .setContentText(getString(R.string.notification_content) + title + "!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
            //endregion

        }
        else if (requestCode == EDIT_MOVIE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditMovie.extraId, -1);
            if (id == -1) {
                Toast.makeText(this, R.string.failed_update, Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditMovie.EXTRA_TITLE);
            int ageRating = data.getIntExtra(AddEditMovie.EXTRA_AGE_RATING,3);
            int length = data.getIntExtra(AddEditMovie.EXTRA_LENGTH,120);
            int seats = data.getIntExtra(AddEditMovie.EXTRA_SEATS_AVAILABLE,40);

            Movie movie = new Movie(title,ageRating,length,seats);
            movie.setId(id);
            movieViewModel.update(movie);

            Toast.makeText(this, R.string.successful_save, Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == BUY_TICKET_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(BuyTicket.extraId, -1);
            if (id == -1) {
                Toast.makeText(this, "Buying failed", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(BuyTicket.EXTRA_TITLE);
            int ageRating = data.getIntExtra(BuyTicket.EXTRA_AGE_RATING,3);
            int length = data.getIntExtra(BuyTicket.EXTRA_LENGTH,120);
            int seats = data.getIntExtra(BuyTicket.EXTRA_SEATS_AVAILABLE,40);

            String qrSeed = title+ageRating+length+seats;
            Ticket ticket = new Ticket(title,qrSeed);
            ticketViewModel.insert(ticket);

//            Movie movie = new Movie(title,ageRating,length,seats - 1); //zabezpieczyc przed zejsciem seats do 0
//            movie.setId(id);
//            movieViewModel.update(movie);

            Toast.makeText(this, "Ticket bought successfully", Toast.LENGTH_SHORT).show();

        }

        else {
            Toast.makeText(this, R.string.failed_save, Toast.LENGTH_SHORT).show();

        }
    }

    private void createNotificationChannel() {
        //only api 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            CharSequence name = getString(R.string.channel_name);


            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}