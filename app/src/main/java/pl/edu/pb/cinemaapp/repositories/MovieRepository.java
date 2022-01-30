package pl.edu.pb.cinemaapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.cinemaapp.entities.Movie;
import pl.edu.pb.cinemaapp.databases.MovieDatabase;
import pl.edu.pb.cinemaapp.daos.MovieDao;

public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public MovieRepository(Application application){
        MovieDatabase database = MovieDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    public void insert(Movie movie){
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            movieDao.insert(movie);
        });
    }
    public void update(Movie movie){
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            movieDao.update(movie);
        });
    }
    public void delete(Movie movie){
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            movieDao.delete(movie);
        });

    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

}
