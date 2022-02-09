package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiskData {
    @SerializedName("grass_pollen")
    @Expose
    private String grassPollen;

    @SerializedName("tree_pollen")
    @Expose
    private String treePollen;

    @SerializedName("weed_pollen")
    @Expose
    private String weedPollen;

    public String getGrassPollen() {
        return grassPollen;
    }

    public void setGrassPollen(String grassPollen) {
        this.grassPollen = grassPollen;
    }

    public String getTreePollen() {
        return treePollen;
    }

    public void setTreePollen(String treePollen) {
        this.treePollen = treePollen;
    }

    public String getWeedPollen() {
        return weedPollen;
    }

    public void setWeedPollen(String weedPollen) {
        this.weedPollen = weedPollen;
    }

}
