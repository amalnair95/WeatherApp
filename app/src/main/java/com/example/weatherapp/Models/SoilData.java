package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoilData {
    @SerializedName("soil_temperature")
    @Expose
    private Double soilTemp;

    @SerializedName("soil_moisture")
    @Expose
    private Double soilMoist;

    public Double getSoilTemp() {
        return soilTemp;
    }

    public void setSoilTemp(Double soilTemp) {
        this.soilTemp = soilTemp;
    }

    public Double getSoilMoist() {
        return soilMoist;
    }

    public void setSoilMoist(Double soilMoist) {
        this.soilMoist = soilMoist;
    }

}
