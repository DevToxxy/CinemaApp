package pl.edu.pb.cinemaapp.daos;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.edu.pb.cinemaapp.entities.Ticket;

@Dao
public interface TicketDao {

    @Insert
    void insert(Ticket ticket);

    @Update
    void update(Ticket ticket);

    @Delete
    void delete(Ticket ticket);

    @Query("SELECT * FROM ticket_table")
    LiveData<List<Ticket>> getAllTickets();

}