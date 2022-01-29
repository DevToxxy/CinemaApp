package pl.edu.pb.cinemaapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ticket_table")
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String movieTitle;
    private String stringForQR;

    public Ticket(String movieTitle, String stringForQR) {
        this.movieTitle = movieTitle;
        this.stringForQR = stringForQR;
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
}
