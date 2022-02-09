package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountData {
    @SerializedName("grass_pollen")
    @Expose
    private Integer grassPollen;

    @SerializedName("tree_pollen")
    @Expose
    private Integer treePollen;

    @SerializedName("weed_pollen")
    @Expose
    private Integer weedPollen;

    public Integer getGrassPollen() {
        return grassPollen;
    }

    public void setGrassPollen(Integer grassPollen) {
        this.grassPollen = grassPollen;
    }

    public Integer getTreePollen() {
        return treePollen;
    }

    public void setTreePollen(Integer treePollen) {
        this.treePollen = treePollen;
    }

    public Integer getWeedPollen() {
        return weedPollen;
    }

    public void setWeedPollen(Integer weedPollen) {
        this.weedPollen = weedPollen;
    }

}
