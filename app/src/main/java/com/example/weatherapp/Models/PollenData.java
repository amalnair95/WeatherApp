package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PollenData {

    @SerializedName("Count")
    @Expose
    private CountData countData;

    @SerializedName("Risk")
    @Expose
    private RiskData riskData;

    public CountData getCountData() {
        return countData;
    }

    public void setCountData(CountData countData) {
        this.countData = countData;
    }

    public RiskData getRiskData() {
        return riskData;
    }

    public void setRiskData(RiskData riskData) {
        this.riskData = riskData;
    }
}
