package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDetails {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("calories")
    @Expose
    private Double calories;

    @SerializedName("fat_total_g")
    @Expose
    private Double fat;

    @SerializedName("fat_saturated_g")
    @Expose
    private Double saturatedFat;

    @SerializedName("protein_g")
    @Expose
    private Double protein;

    @SerializedName("sodium_mg")
    @Expose
    private Double sodium;

    @SerializedName("potassium_mg")
    @Expose
    private Double potassium;

    @SerializedName("cholesterol_mg")
    @Expose
    private Double cholesterol;

    @SerializedName("carbohydrates_total_g")
    @Expose
    private Double carbohydrates;

    @SerializedName("fiber_g")
    @Expose
    private Double fiber;

    @SerializedName("sugar_g")
    @Expose
    private Double sugar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getSodium() {
        return sodium;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public Double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public Double getSugar() {
        return sugar;
    }

    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }
}
