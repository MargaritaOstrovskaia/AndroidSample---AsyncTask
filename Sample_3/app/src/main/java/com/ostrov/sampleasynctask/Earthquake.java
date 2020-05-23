package com.ostrov.sampleasynctask;

class Earthquake {
    private double magnitude;
    private String location;
    private long time;
    private String url;

    Earthquake(double magnitude, String location, long time, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
        this.url = url;
    }

    double getMagnitude() {
        return magnitude;
    }

    String getLocation() {
        return location;
    }

    long getTime() {
        return time;
    }

    String getUrl() {
        return url;
    }
}