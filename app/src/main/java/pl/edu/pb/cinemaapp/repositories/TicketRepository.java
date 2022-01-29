package pl.edu.pb.cinemaapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.cinemaapp.databases.MovieDatabase;
import pl.edu.pb.cinemaapp.entities.Ticket;
import pl.edu.pb.cinemaapp.daos.TicketDao;

public class TicketRepository {
    private TicketDao ticketDao;
    private LiveData<List<Ticket>> allTickets;

    public TicketRepository(Application application) {
        MovieDatabase database = MovieDatabase.getInstance(application);
        ticketDao = database.ticketDao();
        allTickets = ticketDao.getAllTickets();
    }

    public void insert(Ticket ticket) {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            ticketDao.insert(ticket);
        });
    }

    public void update(Ticket ticket) {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            ticketDao.update(ticket);
        });
    }

    public void delete(Ticket ticket) {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            ticketDao.delete(ticket);
        });

    }

    public LiveData<List<Ticket>> getAllTickets() {
        return allTickets;
    }
}