package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirResults {

    @SerializedName("stations")
    @Expose
    private List<AirData> results;

    public List<AirData> getResults() {
        return results;
    }

    public void setResults(List<AirData> results) {
        this.results = results;
    }
}
