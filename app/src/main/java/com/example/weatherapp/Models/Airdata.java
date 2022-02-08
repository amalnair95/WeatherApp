package com.example.weatherapp.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class AirData implements Parcelable {

    @SerializedName("CO")
    @Expose
    private Double carbonMonoxide;

    @SerializedName("NO2")
    @Expose
    private Double nitrogenOxide;

    @SerializedName("OZONE")
    @Expose
    private Double ozone;


    @SerializedName("PM10")
    @Expose
    private Double PM10;


    @SerializedName("PM25")
    @Expose
    private Double PM25;


    @SerializedName("SO2")
    @Expose
    private Double sulphurDioxide;

    @SerializedName("AQI")
    @Expose
    private Double airQualityIndex;

    public Double getCarbonMonoxide() {
        return carbonMonoxide;
    }

    public void setCarbonMonoxide(Double carbonMonoxide) {
        this.carbonMonoxide = carbonMonoxide;
    }

    public Double getNitrogenOxide() {
        return nitrogenOxide;
    }

    public void setNitrogenOxide(Double nitrogenOxide) {
        this.nitrogenOxide = nitrogenOxide;
    }

    public Double getOzone() {
        return ozone;
    }

    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public Double getPM10() {
        return PM10;
    }

    public void setPM10(Double PM10) {
        this.PM10 = PM10;
    }

    public Double getPM25() {
        return PM25;
    }

    public void setPM25(Double PM25) {
        this.PM25 = PM25;
    }

    public Double getSulphurDioxide() {
        return sulphurDioxide;
    }

    public void setSulphurDioxide(Double sulphurDioxide) {
        this.sulphurDioxide = sulphurDioxide;
    }

    public Double getAirQualityIndex() {
        return airQualityIndex;
    }

    public void setAirQualityIndex(Double airQualityIndex) {
        this.airQualityIndex = airQualityIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
