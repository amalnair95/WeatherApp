package com.example.weatherapp.apiClient

import com.example.weatherapp.models.AirResults
import com.example.weatherapp.models.WeatherResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiInterface {

    @Headers("Content-Type: application/json","x-api-key: 9ba29c96f9a67ee5abbe6c8ec146a393d56f422db854dc4d521bf37e4a9b30d3")
    @GET("/weather/latest/by-lat-lng")
    fun getWeatherDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<WeatherResults>

    @Headers("Content-Type: application/json","x-api-key: 9ba29c96f9a67ee5abbe6c8ec146a393d56f422db854dc4d521bf37e4a9b30d3")
    @GET("/latest/by-lat-lng")
    fun getAirDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<AirResults>

}