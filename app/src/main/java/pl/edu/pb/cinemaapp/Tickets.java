package pl.edu.pb.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import pl.edu.pb.cinemaapp.adapters.TicketAdapter;
import pl.edu.pb.cinemaapp.viewmodels.TicketViewModel;

public class Tickets extends AppCompatActivity implements SensorEventListener {

    private TicketViewModel ticketViewModel;

    private Sensor lightSensor;
    private SensorManager sensorManager;
    private int currentBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        setTitle(R.string.activity_title_tickets);

        askPermission(this);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        RecyclerView recyclerView = findViewById(R.id.ticket_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TicketAdapter ticketAdapter = new TicketAdapter(Tickets.this);
        recyclerView.setAdapter(ticketAdapter);

        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel.class);
        ticketViewModel.getAllTickets().observe(this, tickets -> ticketAdapter.submitList(tickets));
        //ticketViewModel.deleteAllTickets(); //for cleaning tickets from DB
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_movie_edit);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(lightSensor != null){
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] < 200)
        {
            Toast.makeText(this, getString(R.string.raise_brightness), Toast.LENGTH_SHORT).show();

            try {
                currentBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,255);

        }
        else return;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void askPermission(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if(Settings.System.canWrite(context)){
                //permission is granted
            }
            else{
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                context.startActivity(intent);
            }
    }

    @Override
    protected void onStop() {
        super.onStop();
        android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,currentBrightness);

    }
}