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

}