package com.example.weatherapp.apiClient

import com.example.weatherapp.models.*
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

    @Headers("Content-Type: application/json","x-api-key: 9ba29c96f9a67ee5abbe6c8ec146a393d56f422db854dc4d521bf37e4a9b30d3")
    @GET("/latest/pollen/by-lat-lng")
    fun getPollenDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<PollenResults>

    @Headers("Content-Type: application/json","x-api-key: 9ba29c96f9a67ee5abbe6c8ec146a393d56f422db854dc4d521bf37e4a9b30d3")
    @GET("/soil/latest/by-lat-lng")
    fun getSoilDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<SoilResults>

    @Headers("Content-Type: application/json","x-api-key: 9ba29c96f9a67ee5abbe6c8ec146a393d56f422db854dc4d521bf37e4a9b30d3")
    @GET("/latest/fire")
    fun getFireDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<FireResults>

}