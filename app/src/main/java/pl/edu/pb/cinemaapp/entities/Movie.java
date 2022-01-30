package pl.edu.pb.cinemaapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private int age;
    private int length;
    private int seats;

    public Movie(){
    }

    public Movie(String title, int age, int length, int seats) {
        this.title = title;
        this.age = age;
        this.length = length;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
