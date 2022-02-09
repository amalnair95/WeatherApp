package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PollenResults {
    public List<PollenData> getResults() {
        return results;
    }

    public void setResults(List<PollenData> results) {
        this.results = results;
    }

    @SerializedName("data")
    @Expose
    private List<PollenData> results;
}
