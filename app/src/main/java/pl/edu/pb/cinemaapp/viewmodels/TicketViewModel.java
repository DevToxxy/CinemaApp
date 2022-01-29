package pl.edu.pb.cinemaapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.cinemaapp.entities.Ticket;
import pl.edu.pb.cinemaapp.repositories.TicketRepository;

public class TicketViewModel extends AndroidViewModel {
    private TicketRepository repository;
    private LiveData<List<Ticket>> allTickets;

    public TicketViewModel(@NonNull Application application) {
        super(application);
        repository = new TicketRepository(application);
        allTickets = repository.getAllTickets();
    }

    public void insert(Ticket ticket){
        repository.insert(ticket);
    }
    public void update(Ticket ticket){
        repository.update(ticket);
    }
    public void delete(Ticket ticket){
        repository.delete(ticket);
    }

    public LiveData<List<Ticket>> getAllTickets() {
        return allTickets;
    }
}
