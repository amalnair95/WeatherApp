package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoilResults {
    @SerializedName("data")
    @Expose
    private List<SoilData> results;

    public List<SoilData> getResults() {
        return results;
    }

    public void setResults(List<SoilData> results) {
        this.results = results;
    }
}
