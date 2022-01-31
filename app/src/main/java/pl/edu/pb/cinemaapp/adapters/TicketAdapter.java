package pl.edu.pb.cinemaapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import lombok.NonNull;
import pl.edu.pb.cinemaapp.R;
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
                    && newItem.getStringForQR().equals(oldItem.getStringForQR())
                    && newItem.getDate().equals(oldItem.getDate());
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

        //region QR Code
        String qrText = currentTicket.getStringForQR().trim();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(qrText, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);

            Glide.with(context)
                    .asBitmap()
                    .load(bitmap)
                    .into(holder.pictureImageView);

            InputMethodManager manager = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE
            );
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //endregion
        holder.movieTitleTextView.setText(currentTicket.getMovieTitle());
        holder.movieDateTextView.setText(String.valueOf(currentTicket.getDate()));


    }


    public Ticket getTicketAt(int position){
        return getItem(position);
    }

    protected class TicketHolder extends RecyclerView.ViewHolder{
        private TextView movieTitleTextView;
        private TextView movieDateTextView;

        private ImageView pictureImageView;


        public TicketHolder(@NonNull View itemView) {
            super(itemView);

            movieTitleTextView = itemView.findViewById(R.id.one_ticket_movie_title);
            movieDateTextView = itemView.findViewById(R.id.one_ticket_date);

            pictureImageView = itemView.findViewById(R.id.one_ticket_picture);

        }

    }
}
