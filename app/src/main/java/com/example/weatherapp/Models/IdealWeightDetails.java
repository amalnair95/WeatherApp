package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdealWeightDetails {
    @SerializedName("data")
    @Expose
    private IdealWeight results;

    public IdealWeight getResults() {
        return results;
    }

    public void setResults(IdealWeight results) {
        this.results = results;
    }

}
