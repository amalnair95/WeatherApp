package com.example.weatherapp.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class WeatherData implements Parcelable {

    @SerializedName("temperature")
    @Expose
    private String temperature;

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("humidity")
    @Expose
    private String humidity;

    @SerializedName("windSpeed")
    @Expose
    private String windSpeed;

    @SerializedName("apparentTemperature")
    @Expose
    private String apparentTemperature;

    @SerializedName("dewPoint")
    @Expose
    private String dewPoint;

    @SerializedName("visibility")
    @Expose
    private String visibility;


    @SerializedName("pressure")
    @Expose
    private String pressure;

    @SerializedName("cloudCover")
    @Expose
    private String cloudCover;

    @SerializedName("windBearing")
    @Expose
    private String windBearing;


    @SerializedName("ozone")
    @Expose
    private String ozone;

    public String getOzone() {
        return ozone;
    }

    public void setOzone(String ozone) {
        this.ozone = ozone;
    }


    public String getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(String windBearing) {
        this.windBearing = windBearing;
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(String cloudCover) {
        this.cloudCover = cloudCover;
    }


    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }


    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getApparentTemperature() {
        return apparentTemperature;
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

    public void setApparentTemperature(String apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTemperature() {
        return temperature;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
