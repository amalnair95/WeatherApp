package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherResults {
    @SerializedName("data")
    @Expose
    private WeatherData results;

    public WeatherData getResults() {
        return results;
    }

    public void setResults(WeatherData results) {
        this.results = results;
    }
}

