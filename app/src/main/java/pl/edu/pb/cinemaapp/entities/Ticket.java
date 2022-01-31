package pl.edu.pb.cinemaapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "ticket_table")
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String movieTitle;
    private String stringForQR;
    private Calendar date;


    public Ticket(String movieTitle, String stringForQR, Calendar date) {
        this.movieTitle = movieTitle;
        this.stringForQR = stringForQR;
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getStringForQR() {
        return stringForQR;
    }

    public void setStringForQR(String stringForQR) {
        this.stringForQR = stringForQR;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
