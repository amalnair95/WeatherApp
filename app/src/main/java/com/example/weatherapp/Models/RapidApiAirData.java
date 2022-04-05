package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapidApiAirData {
    @SerializedName("CO")
    @Expose
    private CO co;

    @SerializedName("NO2")
    @Expose
    private NO2 no2;

    @SerializedName("O3")
    @Expose
    private O3 o3;

    @SerializedName("SO2")
    @Expose
    private SO2 so2;

    @SerializedName("PM2.5")
    @Expose
    private PM25 pm25;

    @SerializedName("PM10")
    @Expose
    private PM10 pm10;

    @SerializedName("overall_aqi")
    @Expose
    private Double aqi;

    public NO2 getNo2() {
        return no2;
    }

    public void setNo2(NO2 no2) {
        this.no2 = no2;
    }

    public O3 getO3() {
        return o3;
    }

    public void setO3(O3 o3) {
        this.o3 = o3;
    }

    public SO2 getSo2() {
        return so2;
    }

    public void setSo2(SO2 so2) {
        this.so2 = so2;
    }

    public PM25 getPm25() {
        return pm25;
    }

    public void setPm25(PM25 pm25) {
        this.pm25 = pm25;
    }

    public PM10 getPm10() {
        return pm10;
    }

    public void setPm10(PM10 pm10) {
        this.pm10 = pm10;
    }

    public Double getAqi() {
        return aqi;
    }

    public void setAqi(Double aqi) {
        this.aqi = aqi;
    }

    public CO getCo() {
        return co;
    }

    public void setCo(CO co) {
        this.co = co;
    }
}
