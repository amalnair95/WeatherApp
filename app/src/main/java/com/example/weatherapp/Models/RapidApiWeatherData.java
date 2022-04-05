package com.example.weatherapp.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class RapidApiWeatherData implements Parcelable {

    @SerializedName("temp")
    @Expose
    private String temperature;

    @SerializedName("humidity")
    @Expose
    private String humidity;

    @SerializedName("wind_speed")
    @Expose
    private String windSpeed;

    @SerializedName("feels_like")
    @Expose
    private String apparentTemperature;

    @SerializedName("cloud_pct")
    @Expose
    private String cloudPrecipitation;

    @SerializedName("wind_degrees")
    @Expose
    private String windDegree;

    @SerializedName("min_temp")
    @Expose
    private String minTemp;

    @SerializedName("max_temp")
    @Expose
    private String maxTemp;

    @SerializedName("sunrise")
    @Expose
    private String sunrise;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(String apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public String getCloudPrecipitation() {
        return cloudPrecipitation;
    }

    public void setCloudPrecipitation(String cloudPrecipitation) {
        this.cloudPrecipitation = cloudPrecipitation;
    }

    public String getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(String windDegree) {
        this.windDegree = windDegree;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    @SerializedName("sunset")
    @Expose
    private String sunset;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
