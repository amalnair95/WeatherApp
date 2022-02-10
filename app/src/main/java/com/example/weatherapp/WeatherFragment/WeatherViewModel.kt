package com.example.weatherapp.weatherFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.apiClient.WeatherApiClient
import com.example.weatherapp.apiClient.WeatherApiInterface
import com.example.weatherapp.models.*
import retrofit2.Call
import retrofit2.Response

class WeatherViewModel:ViewModel() {
    private val TAG: String = WeatherViewModel::class.java.simpleName
    private lateinit var call: Call<WeatherResults>
    private lateinit var airCall: Call<AirResults>
    private lateinit var pollenCall: Call<PollenResults>
    private lateinit var soilCall: Call<SoilResults>

    var resultLiveDataList: MutableLiveData<WeatherData> = MutableLiveData()
    var airResultLiveDataList: MutableLiveData<List<AirData>> = MutableLiveData()
    var pollenResultLiveDataList: MutableLiveData<List<PollenData>> = MutableLiveData()
    var soilResultLiveDataList: MutableLiveData<List<SoilData>> = MutableLiveData()


    fun clearResultSet(){
        airResultLiveDataList= MutableLiveData()
        resultLiveDataList = MutableLiveData()
        pollenResultLiveDataList = MutableLiveData()
        soilResultLiveDataList = MutableLiveData()
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

    fun getAirDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        airCall = apiInterface.getAirDetail(lat,long)
        airCall.enqueue(object : retrofit2.Callback<AirResults> {
            override fun onResponse(call: Call<AirResults>, response: Response<AirResults>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  airData = response.body()!!.results
                    airResultLiveDataList.postValue(airData)
                }
            }

            override fun onFailure(call: Call<AirResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getPollenDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        pollenCall = apiInterface.getPollenDetail(lat,long)
        pollenCall.enqueue(object : retrofit2.Callback<PollenResults> {
            override fun onResponse(call: Call<PollenResults>, response: Response<PollenResults>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  airData = response.body()!!.results
                    pollenResultLiveDataList.postValue(airData)
                }
            }

            override fun onFailure(call: Call<PollenResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getSoilDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        soilCall = apiInterface.getSoilDetail(lat,long)
        soilCall.enqueue(object : retrofit2.Callback<SoilResults> {
            override fun onResponse(call: Call<SoilResults>, response: Response<SoilResults>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  soilData = response.body()!!.results
                    soilResultLiveDataList.postValue(soilData)
                }
            }

            override fun onFailure(call: Call<SoilResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
}