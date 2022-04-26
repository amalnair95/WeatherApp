package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyMassIndex {

    @SerializedName("bmi")
    @Expose
    private String bmi;

    @SerializedName("health")
    @Expose
    private String health;

    @SerializedName("healthy_bmi_range")
    @Expose
    private String healthy_bmi_range;

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHealthy_bmi_range() {
        return healthy_bmi_range;
    }

    public void setHealthy_bmi_range(String healthy_bmi_range) {
        this.healthy_bmi_range = healthy_bmi_range;
    }
}
