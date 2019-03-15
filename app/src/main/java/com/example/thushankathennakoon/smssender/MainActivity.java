package com.example.thushankathennakoon.smssender;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String deviceId;
    GPSListener listener;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Assign device Id and start GPSListener
        try {
            deviceId = new GetDeviceIdTask().execute(this).get();
            listener = new GPSListener(this, 10000);
        } catch (SecurityException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // This method is run when Button clicked in UI
    public void onClick(View view) {

        Location location = null;
        TextView textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
        TextView textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        TextView textViewResponse = (TextView) findViewById(R.id.textViewResponse);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);

        textViewResponse.setText("Please Wait");

        try {
            // Save location to variable if available
            location = new GetLocationTask().execute(listener).get();
        } catch (SecurityException | InterruptedException | ExecutionException e) {
            textViewResponse.setText("An error occured");
            e.printStackTrace();
        }

        if (location == null) {
            textViewResponse.setText("Location unavailable at the moment");
            return;
        }


        // Update textViews with new Location
        textViewLongitude.setText(String.valueOf(location.getLongitude()));
        textViewLatitude.setText(String.valueOf(location.getLatitude()));

        //Create dataObject from available data
        DataObject dataObject = new DataObject(
                deviceId,
                location.getLatitude(),
                location.getLongitude(),
                spinnerType.getSelectedItem().toString());

        //Upload Coordinates to remote DB, and display returned message in textViewResponse
        new UploadTask(this, textViewResponse).execute(dataObject);
    }
}
