package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FireResults {
    @SerializedName("data")
    @Expose
    private List<FireData> results;

    public List<FireData> getResults() {
        return results;
    }

    public void setResults(List<FireData> results) {
        this.results = results;
    }
}
