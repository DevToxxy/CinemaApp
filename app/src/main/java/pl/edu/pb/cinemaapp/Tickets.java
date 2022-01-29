package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import pl.edu.pb.cinemaapp.adapters.MovieAdapter;
import pl.edu.pb.cinemaapp.adapters.TicketAdapter;
import pl.edu.pb.cinemaapp.viewmodels.TicketViewModel;

public class Tickets extends AppCompatActivity {

    private TicketViewModel ticketViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);



        RecyclerView recyclerView = findViewById(R.id.ticket_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TicketAdapter ticketAdapter = new TicketAdapter(Tickets.this);
        recyclerView.setAdapter(ticketAdapter);

        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel.class);
        ticketViewModel.getAllTickets().observe(this, tickets -> ticketAdapter.submitList(tickets));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_movie_edit);


    }
}