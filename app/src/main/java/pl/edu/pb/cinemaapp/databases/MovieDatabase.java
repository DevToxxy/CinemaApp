package pl.edu.pb.cinemaapp.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.pb.cinemaapp.daos.TicketDao;
import pl.edu.pb.cinemaapp.entities.Movie;
import pl.edu.pb.cinemaapp.daos.MovieDao;
import pl.edu.pb.cinemaapp.entities.Ticket;

@Database(entities = {Movie.class, Ticket.class}, version = 4)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract MovieDao movieDao();
    public abstract TicketDao ticketDao();

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class, "movie_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            MovieDatabase.databaseWriteExecutor.execute(() -> {
                MovieDao dao = instance.movieDao();
                dao.insert(new Movie("Tenet",15, 170, 40));
                dao.insert(new Movie("Interstellar",18, 160, 40));
                dao.insert(new Movie("Inception",18, 155, 40));
            });
            //new PopulateDbAsyncTask(instance).execute();
        }
    };

//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private MovieDao movieDao;
//
//        private PopulateDbAsyncTask(MovieDatabase db){
//            movieDao = db.movieDao();
//        }
//
//        @Override
//        protected Void doInBackground (Void... voids){
//            movieDao.insert(new Movie("Tenet",15, 170));
//            movieDao.insert(new Movie("Interstellar",18, 160));
//            movieDao.insert(new Movie("Inception",18, 155));
//
//            return null;
//        }
//    }
}
