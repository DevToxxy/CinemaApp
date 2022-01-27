package pl.edu.pb.cinemaapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieStorage {
        private static final MovieStorage movieStorage = new MovieStorage();

        private final List<Movie> movies;

        public static MovieStorage getInstance() {
            return movieStorage;
        }

        private MovieStorage() {
            movies = new ArrayList<>();
            fillMovieStorage(); //for testing //TODO: get rid of
        }

        public List<Movie> getMovies() {
            return movies;
        }

        public Movie getSingleMovie(UUID movieId) {
            Movie foundMovie = new Movie();
            for (Movie movie : movies) {
                if (movie.getId().equals(movieId)) {
                    foundMovie = movie;
                }
            }
            return foundMovie;
        }

        public void addMovie(Movie movie){
            movies.add(movie);
        }

        //for testing //TODO: get rid of
        public void fillMovieStorage(){
            for (int i = 1; i <= 4; i++) {
                Movie movie = new Movie();
                movie.setTitle("Test");
                movie.setAge(13);
                movie.setLength(180);
                movies.add(movie);
            }
        }

        public void setMovie(UUID id, Movie movie) {
            for (Movie mov : movies) {
                if (mov.getId().equals(id)) {
                    mov.setLength(movie.getLength()) ;
                    mov.setTitle(movie.getTitle()) ;
                    mov.setAge(movie.getAge());
                }
            }
        }
}
