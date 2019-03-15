package com.example.thushankathennakoon.smssender;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class UploadTask extends AsyncTask<DataObject, Void, String> {

    private final TextView textView;

    public UploadTask(Context context, TextView textView) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, 0);
        }
        this.textView = textView;
    }

    @Override
    /* Upload location and get response from server */
    protected String doInBackground(DataObject... dataObjects) {
        try {
            URL url = new URL("http://ictmind.com/thusanka/gps/addloc_m.php");
            // Temporary URL for testing
            url = new URL("https://truckinfo.000webhostapp.com/addloc_m.php");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("lat", String.valueOf(dataObjects[0].getLatitude()));
                jsonObject.put("lng", String.valueOf(dataObjects[0].getLongitude()));
                jsonObject.put("diviceId", String.valueOf(dataObjects[0].getDeviceId()));
                jsonObject.put("type", String.valueOf(dataObjects[0].getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();

            // Write output to server
            OutputStreamWriter stream = new OutputStreamWriter(connection.getOutputStream());
            stream.write(jsonObject.toString());
            stream.flush();
            stream.close();

            // Get and return response from server
            return convertStreamToString(connection.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Write response from server to textView
    protected void onPostExecute(String page) {
        textView.setText(page);
    }

    /* Temporary method to get HTTP response for the request */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
