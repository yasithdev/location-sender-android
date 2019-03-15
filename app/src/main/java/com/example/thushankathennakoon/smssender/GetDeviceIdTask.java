package com.example.thushankathennakoon.smssender;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * Created by yasith on 1/16/17.
 */

public class GetDeviceIdTask extends AsyncTask<Context, Void, String> {
    @SuppressLint("HardwareIds")
    @Override
    protected String doInBackground(Context... contexts) throws SecurityException {
        if (ActivityCompat.checkSelfPermission(contexts[0], Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission to get device Id
            ActivityCompat.requestPermissions((Activity) contexts[0], new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
        if (ActivityCompat.checkSelfPermission(contexts[0], Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Return null value if permission not granted
            throw new SecurityException("No permission to get device Id");
        }
        return ((TelephonyManager) contexts[0].getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}
