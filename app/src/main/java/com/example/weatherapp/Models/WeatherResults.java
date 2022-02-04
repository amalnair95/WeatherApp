package com.example.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

