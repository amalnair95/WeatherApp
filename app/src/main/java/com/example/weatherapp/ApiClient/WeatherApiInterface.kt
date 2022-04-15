package com.example.weatherapp.apiClient

import com.example.weatherapp.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiInterface {

    @Headers("Content-Type: application/json","x-api-key: d82707fe1020a0bbfb2b3b3857734d7ba234bb065ea5f05932f4e644b56cdefd")
    @GET("/weather/latest/by-lat-lng")
    fun getWeatherDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<WeatherResults>

    @Headers("Content-Type: application/json","x-api-key: d82707fe1020a0bbfb2b3b3857734d7ba234bb065ea5f05932f4e644b56cdefd")
    @GET("/latest/by-lat-lng")
    fun getAirDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<AirResults>

    @Headers("Content-Type: application/json","x-api-key: d82707fe1020a0bbfb2b3b3857734d7ba234bb065ea5f05932f4e644b56cdefd")
    @GET("/latest/pollen/by-lat-lng")
    fun getPollenDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<PollenResults>

    @Headers("Content-Type: application/json","x-api-key: d82707fe1020a0bbfb2b3b3857734d7ba234bb065ea5f05932f4e644b56cdefd")
    @GET("/soil/latest/by-lat-lng")
    fun getSoilDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<SoilResults>

    @Headers("Content-Type: application/json","x-api-key: d82707fe1020a0bbfb2b3b3857734d7ba234bb065ea5f05932f4e644b56cdefd")
    @GET("/latest/fire")
    fun getFireDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<FireResults>


    @Headers("X-Api-Key: aRfzVzRyKkS4os8j1UxwPA==gDJWVcU57YYvYe6t")
    @GET("/v1/weather")
    fun getRapidApiWeatherDetail(@Query("lat") lat:Double, @Query("lon") long:Double):Call<RapidApiWeatherData>

    @Headers("X-Api-Key: aRfzVzRyKkS4os8j1UxwPA==gDJWVcU57YYvYe6t")
    @GET("/v1/airquality")
    fun getRapidApiAirDetail(@Query("lat") lat:Double, @Query("lon") long:Double):Call<RapidApiAirData>

    @Headers("X-Api-Key: aRfzVzRyKkS4os8j1UxwPA==gDJWVcU57YYvYe6t")
    @GET("/v1/quotes")
    fun getRapidApiQuoteDetail(@Query("category") category:String):Call<List<QuoteData>>

    @Headers("X-Api-Key: aRfzVzRyKkS4os8j1UxwPA==gDJWVcU57YYvYe6t")
    @GET("/v1/nutrition")
    fun getFoodDetail(@Query("query") foodItem:String):Call<List<FoodDetails>>

    @Headers("X-Api-Key: aRfzVzRyKkS4os8j1UxwPA==gDJWVcU57YYvYe6t")
    @GET("/v1/recipe")
    fun getFoodRecipeDetail(@Query("query") foodItem:String):Call<List<FoodRecipeDetails>>

    @Headers("X-Api-Key: aRfzVzRyKkS4os8j1UxwPA==gDJWVcU57YYvYe6t")
    @GET("/v1/dictionary")
    fun getWordDetail(@Query("word") word:String):Call<WordDetails>
}