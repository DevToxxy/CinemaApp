package pl.edu.pb.cinemaapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

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
        new InsertMovieAsyncTask(movieDao).execute(movie);
    }
    public void update(Movie movie){
        new UpdateMovieAsyncTask(movieDao).execute(movie);
    }
    public void delete(Movie movie){
        new DeleteMovieAsyncTask(movieDao).execute(movie);

    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        private InsertMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies){
            movieDao.insert(movies[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        private UpdateMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies){
            movieDao.update(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        private DeleteMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies){
            movieDao.delete(movies[0]);
            return null;
        }
    }
}
