package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyMassIndexDetails {
    @SerializedName("data")
    @Expose
    private BodyMassIndex results;

    public BodyMassIndex getResults() {
        return results;
    }

    public void setResults(BodyMassIndex results) {
        this.results = results;
    }
}
