package com.example.weatherapp.WeatherFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.ApiClient.WeatherApiClient
import com.example.weatherapp.ApiClient.WeatherApiInterface
import com.example.weatherapp.Models.WeatherData
import com.example.weatherapp.Models.WeatherResults
import retrofit2.Call
import retrofit2.Response

class WeatherViewModel:ViewModel() {
    private val TAG: String = WeatherViewModel::class.java.simpleName
    private lateinit var call: Call<WeatherResults>

    var resultLiveDataList: MutableLiveData<WeatherData> = MutableLiveData()


    fun clearResultSet(){
        resultLiveDataList = MutableLiveData()
    }

    fun getWeatherDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        call = apiInterface.getWeatherDetail(lat,long)


        call.enqueue(object : retrofit2.Callback<WeatherResults> {
            override fun onResponse(call: Call<WeatherResults>, response: Response<WeatherResults>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  weatherData = response.body()!!.results
                    resultLiveDataList.postValue(weatherData)
                }
            }

            override fun onFailure(call: Call<WeatherResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
}