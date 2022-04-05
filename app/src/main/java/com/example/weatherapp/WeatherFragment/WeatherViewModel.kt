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
    private lateinit var call: Call<RapidApiWeatherData>
    private lateinit var airCall: Call<RapidApiAirData>
    private lateinit var pollenCall: Call<PollenResults>
    private lateinit var soilCall: Call<SoilResults>
    private lateinit var fireCall: Call<FireResults>

    var resultLiveDataList: MutableLiveData<RapidApiWeatherData> = MutableLiveData()
    var airResultLiveDataList: MutableLiveData<RapidApiAirData> = MutableLiveData()
    var pollenResultLiveDataList: MutableLiveData<List<PollenData>> = MutableLiveData()
    var soilResultLiveDataList: MutableLiveData<List<SoilData>> = MutableLiveData()
    var fireResultLiveDataList: MutableLiveData<List<FireData>> = MutableLiveData()


    fun clearResultSet(){
        airResultLiveDataList= MutableLiveData()
        resultLiveDataList = MutableLiveData()
        pollenResultLiveDataList = MutableLiveData()
        soilResultLiveDataList = MutableLiveData()
        fireResultLiveDataList = MutableLiveData()
    }

    fun getWeatherDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        //val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        val apiInterface: WeatherApiInterface = WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        call = apiInterface.getRapidApiWeatherDetail(lat,long)


        call.enqueue(object : retrofit2.Callback<RapidApiWeatherData> {
            override fun onResponse(call: Call<RapidApiWeatherData>, response: Response<RapidApiWeatherData>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  weatherData = response.body()!!
                    resultLiveDataList.postValue(weatherData)
                }
            }

            override fun onFailure(call: Call<RapidApiWeatherData>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getAirDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface = WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        //airCall = apiInterface.getAirDetail(lat,long)
        airCall = apiInterface.getRapidApiAirDetail(lat,long)
        airCall.enqueue(object : retrofit2.Callback<RapidApiAirData> {
            override fun onResponse(call: Call<RapidApiAirData>, response: Response<RapidApiAirData>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  airData = response.body()!!
                    airResultLiveDataList.postValue(airData)
                }
            }

            override fun onFailure(call: Call<RapidApiAirData>, t: Throwable) {
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

    fun getFireDetail(lat: Double,long:Double) {
        Log.d(TAG,"API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        fireCall = apiInterface.getFireDetail(lat,long)
        fireCall.enqueue(object : retrofit2.Callback<FireResults> {
            override fun onResponse(call: Call<FireResults>, response: Response<FireResults>) {
                if (response.body()!= null && response.isSuccessful) {
                    val  fireData = response.body()!!.results
                    fireResultLiveDataList.postValue(fireData)
                }
            }

            override fun onFailure(call: Call<FireResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
}