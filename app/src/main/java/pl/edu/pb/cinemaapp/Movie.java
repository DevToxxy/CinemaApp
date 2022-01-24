package pl.edu.pb.cinemaapp;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private UUID id;



    private String title;
    private int age;
    private int length;

    public Movie(){
        id = UUID.randomUUID();
    }

    public Movie(String title, int age, int length) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.age = age;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", age=" + age +
                ", length=" + length +
                '}';
    }
}
