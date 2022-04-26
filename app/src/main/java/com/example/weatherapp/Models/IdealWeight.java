package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdealWeight {
    @SerializedName("Hamwi")
    @Expose
    private String hamwi;

    @SerializedName("Devine")
    @Expose
    private String devine;

    @SerializedName("Miller")
    @Expose
    private String miller;

    @SerializedName("Robinson")
    @Expose
    private String robinson;

    public String getHamwi() {
        return hamwi;
    }

    public void setHamwi(String hamwi) {
        this.hamwi = hamwi;
    }

    public String getDevine() {
        return devine;
    }

    public void setDevine(String devine) {
        this.devine = devine;
    }

    public String getMiller() {
        return miller;
    }

    public void setMiller(String miller) {
        this.miller = miller;
    }

    public String getRobinson() {
        return robinson;
    }

    public void setRobinson(String robinson) {
        this.robinson = robinson;
    }
}
