package com.example.thushankathennakoon.smssender;

/**
 * Created by yasith on 1/16/17.
 */

public class DataObject {
    private String deviceId;
    private double latitude;
    private double longitude;
    private String type;

    public DataObject(String deviceId, double latitude, double longitude, String type) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
