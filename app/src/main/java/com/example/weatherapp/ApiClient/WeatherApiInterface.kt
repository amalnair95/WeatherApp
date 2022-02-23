package com.example.weatherapp.apiClient

import com.example.weatherapp.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiInterface {

    @Headers("Content-Type: application/json","x-api-key: ea6a1cd870f18a9684497e45764682adafc23e827f4a5d568fbad512a8544dbf")
    @GET("/weather/latest/by-lat-lng")
    fun getWeatherDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<WeatherResults>

    @Headers("Content-Type: application/json","x-api-key: ea6a1cd870f18a9684497e45764682adafc23e827f4a5d568fbad512a8544dbf")
    @GET("/latest/by-lat-lng")
    fun getAirDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<AirResults>

    @Headers("Content-Type: application/json","x-api-key: ea6a1cd870f18a9684497e45764682adafc23e827f4a5d568fbad512a8544dbf")
    @GET("/latest/pollen/by-lat-lng")
    fun getPollenDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<PollenResults>

    @Headers("Content-Type: application/json","x-api-key: ea6a1cd870f18a9684497e45764682adafc23e827f4a5d568fbad512a8544dbf")
    @GET("/soil/latest/by-lat-lng")
    fun getSoilDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<SoilResults>

    @Headers("Content-Type: application/json","x-api-key: ea6a1cd870f18a9684497e45764682adafc23e827f4a5d568fbad512a8544dbf")
    @GET("/latest/fire")
    fun getFireDetail(@Query("lat") lat:Double, @Query("lng") long:Double):Call<FireResults>

}