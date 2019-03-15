package com.example.thushankathennakoon.smssender;

import android.location.Location;
import android.os.AsyncTask;

/**
 * Created by yasith on 1/16/17.
 */

public class GetLocationTask extends AsyncTask<GPSListener, Void, Location> {

    @Override
    protected Location doInBackground(GPSListener... listeners) {
        return listeners[0].getCurrentLocation();
    }
}
