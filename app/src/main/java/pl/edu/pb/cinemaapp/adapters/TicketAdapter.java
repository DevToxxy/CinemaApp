package pl.edu.pb.cinemaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import lombok.NonNull;
import pl.edu.pb.cinemaapp.R;
import pl.edu.pb.cinemaapp.entities.Movie;
import pl.edu.pb.cinemaapp.entities.Ticket;

public class TicketAdapter extends ListAdapter<Ticket, TicketAdapter.TicketHolder> {

    private Context context;

    public TicketAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }
    private static final DiffUtil.ItemCallback<Ticket> DIFF_CALLBACK = new DiffUtil.ItemCallback<Ticket>() {
        @Override
        public boolean areItemsTheSame(@androidx.annotation.NonNull Ticket oldItem, @androidx.annotation.NonNull Ticket newItem) {
            return newItem.getId() == oldItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@androidx.annotation.NonNull Ticket oldItem, @androidx.annotation.NonNull Ticket newItem) {
            return newItem.getMovieTitle().equals(oldItem.getMovieTitle())
                    && newItem.getStringForQR().equals(oldItem.getStringForQR());
        }
    };
    @NonNull
    @Override
    public TicketAdapter.TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_ticket, parent, false);

        return new TicketAdapter.TicketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketHolder holder, int position) {
        Ticket currentTicket = getItem(position);

        holder.movieTitleTextView.setText(currentTicket.getMovieTitle());

        Glide.with(context)
                .load("https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg")
                .into(holder.pictureImageView);


    }


    public Ticket getTicketAt(int position){
        return getItem(position);
    }

    protected class TicketHolder extends RecyclerView.ViewHolder{
        private TextView movieTitleTextView;

        private ImageView pictureImageView;


        public TicketHolder(@NonNull View itemView) {
            super(itemView);

            movieTitleTextView = itemView.findViewById(R.id.one_ticket_movie_title);

            pictureImageView = itemView.findViewById(R.id.one_ticket_picture);

        }

    }
}
