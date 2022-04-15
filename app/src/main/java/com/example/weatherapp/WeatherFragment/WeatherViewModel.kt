package com.example.weatherapp.weatherFragment

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.apiClient.WeatherApiClient
import com.example.weatherapp.apiClient.WeatherApiInterface
import com.example.weatherapp.databaseFiles.DatabaseHelper
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
    private lateinit var foodDetailCall: Call<List<FoodDetails>>
    private lateinit var foodRecipeDetailCall: Call<List<FoodRecipeDetails>>
    private lateinit var wordDetailCall: Call<WordDetails>
    private var dbHelper: DatabaseHelper? = null


    var resultLiveDataList: MutableLiveData<RapidApiWeatherData> = MutableLiveData()
    var airResultLiveDataList: MutableLiveData<RapidApiAirData> = MutableLiveData()
    var pollenResultLiveDataList: MutableLiveData<List<PollenData>> = MutableLiveData()
    var soilResultLiveDataList: MutableLiveData<List<SoilData>> = MutableLiveData()
    var fireResultLiveDataList: MutableLiveData<List<FireData>> = MutableLiveData()
    var foodDetailResultLiveDataList: MutableLiveData<List<FoodDetails>> = MutableLiveData()
    var foodRecipeDetailResultLiveDataList: MutableLiveData<List<FoodRecipeDetails>> = MutableLiveData()
    var wordDetailResultLiveDataList: MutableLiveData<WordDetails> = MutableLiveData()


    fun clearResultSet(){
        airResultLiveDataList= MutableLiveData()
        resultLiveDataList = MutableLiveData()
        pollenResultLiveDataList = MutableLiveData()
        soilResultLiveDataList = MutableLiveData()
        fireResultLiveDataList = MutableLiveData()
        foodDetailResultLiveDataList = MutableLiveData()
        foodRecipeDetailResultLiveDataList = MutableLiveData()
        wordDetailResultLiveDataList = MutableLiveData()
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

    fun getFoodDetails(foodItem:String) {
        val apiInterface: WeatherApiInterface = WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        foodDetailCall = apiInterface.getFoodDetail(foodItem)
        foodDetailCall.enqueue(object : retrofit2.Callback<List<FoodDetails>> {
            override fun onResponse(call: Call<List<FoodDetails>>, response: Response<List<FoodDetails>>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  foodDetailList = response.body()!!
                    Log.d(TAG,"Food Details: $foodDetailList")
                    foodDetailResultLiveDataList.postValue(foodDetailList)
                }
            }
            override fun onFailure(call: Call<List<FoodDetails>>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getFoodRecipeDetails(foodItem:String) {
        val apiInterface: WeatherApiInterface = WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        foodRecipeDetailCall = apiInterface.getFoodRecipeDetail(foodItem)
        foodRecipeDetailCall.enqueue(object : retrofit2.Callback<List<FoodRecipeDetails>> {
            override fun onResponse(call: Call<List<FoodRecipeDetails>>, response: Response<List<FoodRecipeDetails>>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  foodDetailList = response.body()!!
                    Log.d(TAG,"Food Details: $foodDetailList")
                    foodRecipeDetailResultLiveDataList.postValue(foodDetailList)
                }
            }
            override fun onFailure(call: Call<List<FoodRecipeDetails>>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
    fun getWordDetails(word:String) {
        val apiInterface: WeatherApiInterface = WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        wordDetailCall = apiInterface.getWordDetail(word)
        wordDetailCall.enqueue(object : retrofit2.Callback<WordDetails> {
            override fun onResponse(call: Call<WordDetails>, response: Response<WordDetails>) {
                if (response.body() != null && response.isSuccessful && response.body()!= null) {
                    val  wordDetail = response.body()!!
                    Log.d(TAG,"Food Details: $wordDetail")
                    wordDetailResultLiveDataList.postValue(wordDetail)
                }
            }
            override fun onFailure(call: Call<WordDetails>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
    fun getUser(context: Context):String{
        dbHelper = DatabaseHelper(context)
        val checkBiQuery = dbHelper!!.ExecuteBiQuery("Select * from TokenDetails")
        return checkBiQuery.getString(2)
    }
}