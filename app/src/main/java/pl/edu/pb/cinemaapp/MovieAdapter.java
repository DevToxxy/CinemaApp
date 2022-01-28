package pl.edu.pb.cinemaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class MovieAdapter extends ListAdapter<Movie, MovieAdapter.MovieHolder>{
    //private List<Movie> movies = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    public MovieAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }
    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@androidx.annotation.NonNull Movie oldItem, @androidx.annotation.NonNull Movie newItem) {
            if (newItem.getId() == oldItem.getId())
                return true;
            else
                return false;
        }

        @Override
        public boolean areContentsTheSame(@androidx.annotation.NonNull Movie oldItem, @androidx.annotation.NonNull Movie newItem) {
            if(newItem.getTitle().equals(oldItem.getTitle())
                    && newItem.getAge() == oldItem.getAge()
                    && newItem.getLength() == oldItem.getAge())
                return true;
            else
                return false;
        }
    };
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_movie, parent, false);

        MovieHolder holder = new MovieHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie currentMovie = getItem(position);

        holder.titleTextView.setText(currentMovie.getTitle());
        holder.ageTextView.setText(String.valueOf(currentMovie.getAge()));
        holder.lengthTextView.setText(String.valueOf(currentMovie.getLength()));
        Glide.with(context)
                .load("https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg")
                .into(holder.pictureImageView);


    }


    public Movie getMovieAt(int position){
        return getItem(position);
    }


    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected class MovieHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView ageTextView;
        private TextView lengthTextView;
        private ImageView pictureImageView;


        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.one_movie_title);
            ageTextView = itemView.findViewById(R.id.one_movie_age_rating);
            lengthTextView = itemView.findViewById(R.id.one_movie_length);

            pictureImageView = itemView.findViewById(R.id.one_movie_picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }

    }



}
