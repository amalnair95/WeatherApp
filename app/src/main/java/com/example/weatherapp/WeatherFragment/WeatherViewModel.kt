package com.example.weatherapp.weatherFragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.apiClient.WeatherApiClient
import com.example.weatherapp.apiClient.WeatherApiInterface
import com.example.weatherapp.commonMethod.CursorUtils
import com.example.weatherapp.databaseFiles.DatabaseHelper
import com.example.weatherapp.isNullOrEmpty
import com.example.weatherapp.models.*
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response


class WeatherViewModel : ViewModel() {
    private val TAG: String = WeatherViewModel::class.java.simpleName
    private lateinit var call: Call<RapidApiWeatherData>
    private lateinit var airCall: Call<RapidApiAirData>
    private lateinit var pollenCall: Call<PollenResults>
    private lateinit var soilCall: Call<SoilResults>
    private lateinit var fireCall: Call<FireResults>
    private lateinit var foodDetailCall: Call<List<FoodDetails>>
    private lateinit var foodRecipeDetailCall: Call<List<FoodRecipeDetails>>
    private lateinit var wordDetailCall: Call<WordDetails>
    private lateinit var bmiDetailCall: Call<BodyMassIndexDetails>
    private lateinit var idealWeightDetailCall: Call<IdealWeightDetails>
    private var dbHelper: DatabaseHelper? = null


    var resultLiveDataList: MutableLiveData<RapidApiWeatherData> = MutableLiveData()
    var airResultLiveDataList: MutableLiveData<RapidApiAirData> = MutableLiveData()
    var pollenResultLiveDataList: MutableLiveData<List<PollenData>> = MutableLiveData()
    var soilResultLiveDataList: MutableLiveData<List<SoilData>> = MutableLiveData()
    var fireResultLiveDataList: MutableLiveData<List<FireData>> = MutableLiveData()
    var foodDetailResultLiveDataList: MutableLiveData<List<FoodDetails>> = MutableLiveData()
    var foodRecipeDetailResultLiveDataList: MutableLiveData<List<FoodRecipeDetails>> =
        MutableLiveData()
    var wordDetailResultLiveDataList: MutableLiveData<WordDetails> = MutableLiveData()
    var bodyMassIndexResultLiveDataList: MutableLiveData<BodyMassIndexDetails> = MutableLiveData()
    var idealWeightResultLiveDataList: MutableLiveData<IdealWeightDetails> = MutableLiveData()
    var productListLiveDataList: MutableLiveData<MutableList<Products>?> = MutableLiveData()
    var cartLiveDataList: MutableLiveData<List<CartItem>> = MutableLiveData()
    var totalLiveDataList: MutableLiveData<Double> = MutableLiveData()


    fun clearResultSet() {
        airResultLiveDataList = MutableLiveData()
        resultLiveDataList = MutableLiveData()
        pollenResultLiveDataList = MutableLiveData()
        soilResultLiveDataList = MutableLiveData()
        fireResultLiveDataList = MutableLiveData()
        foodDetailResultLiveDataList = MutableLiveData()
        foodRecipeDetailResultLiveDataList = MutableLiveData()
        wordDetailResultLiveDataList = MutableLiveData()
        bodyMassIndexResultLiveDataList = MutableLiveData()
        idealWeightResultLiveDataList = MutableLiveData()
        productListLiveDataList = MutableLiveData()
    }

    fun getWeatherDetail(lat: Double, long: Double) {
        Log.d(TAG, "API called for lat $lat and long $long")
        //val apiInterface: WeatherApiInterface = WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        call = apiInterface.getRapidApiWeatherDetail(lat, long)


        call.enqueue(object : retrofit2.Callback<RapidApiWeatherData> {
            override fun onResponse(
                call: Call<RapidApiWeatherData>,
                response: Response<RapidApiWeatherData>
            ) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val weatherData = response.body()!!
                    resultLiveDataList.postValue(weatherData)
                }
            }

            override fun onFailure(call: Call<RapidApiWeatherData>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getAirDetail(lat: Double, long: Double) {
        Log.d(TAG, "API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        //airCall = apiInterface.getAirDetail(lat,long)
        airCall = apiInterface.getRapidApiAirDetail(lat, long)
        airCall.enqueue(object : retrofit2.Callback<RapidApiAirData> {
            override fun onResponse(
                call: Call<RapidApiAirData>,
                response: Response<RapidApiAirData>
            ) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val airData = response.body()!!
                    airResultLiveDataList.postValue(airData)
                }
            }

            override fun onFailure(call: Call<RapidApiAirData>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getPollenDetail(lat: Double, long: Double) {
        Log.d(TAG, "API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        pollenCall = apiInterface.getPollenDetail(lat, long)
        pollenCall.enqueue(object : retrofit2.Callback<PollenResults> {
            override fun onResponse(call: Call<PollenResults>, response: Response<PollenResults>) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val airData = response.body()!!.results
                    pollenResultLiveDataList.postValue(airData)
                }
            }

            override fun onFailure(call: Call<PollenResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getSoilDetail(lat: Double, long: Double) {
        Log.d(TAG, "API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        soilCall = apiInterface.getSoilDetail(lat, long)
        soilCall.enqueue(object : retrofit2.Callback<SoilResults> {
            override fun onResponse(call: Call<SoilResults>, response: Response<SoilResults>) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val soilData = response.body()!!.results
                    soilResultLiveDataList.postValue(soilData)
                }
            }

            override fun onFailure(call: Call<SoilResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getFireDetail(lat: Double, long: Double) {
        Log.d(TAG, "API called for lat $lat and long $long")
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getApiClient().create(WeatherApiInterface::class.java)
        fireCall = apiInterface.getFireDetail(lat, long)
        fireCall.enqueue(object : retrofit2.Callback<FireResults> {
            override fun onResponse(call: Call<FireResults>, response: Response<FireResults>) {
                if (response.body() != null && response.isSuccessful) {
                    val fireData = response.body()!!.results
                    fireResultLiveDataList.postValue(fireData)
                }
            }

            override fun onFailure(call: Call<FireResults>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getFoodDetails(foodItem: String) {
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        foodDetailCall = apiInterface.getFoodDetail(foodItem)
        foodDetailCall.enqueue(object : retrofit2.Callback<List<FoodDetails>> {
            override fun onResponse(
                call: Call<List<FoodDetails>>,
                response: Response<List<FoodDetails>>
            ) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val foodDetailList = response.body()!!
                    Log.d(TAG, "Food Details: $foodDetailList")
                    foodDetailResultLiveDataList.postValue(foodDetailList)
                }
            }

            override fun onFailure(call: Call<List<FoodDetails>>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getFoodRecipeDetails(foodItem: String) {
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        foodRecipeDetailCall = apiInterface.getFoodRecipeDetail(foodItem)
        foodRecipeDetailCall.enqueue(object : retrofit2.Callback<List<FoodRecipeDetails>> {
            override fun onResponse(
                call: Call<List<FoodRecipeDetails>>,
                response: Response<List<FoodRecipeDetails>>
            ) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val foodDetailList = response.body()!!
                    Log.d(TAG, "Food Details: $foodDetailList")
                    foodRecipeDetailResultLiveDataList.postValue(foodDetailList)
                }
            }

            override fun onFailure(call: Call<List<FoodRecipeDetails>>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getWordDetails(word: String) {
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getRapidApiClient().create(WeatherApiInterface::class.java)
        wordDetailCall = apiInterface.getWordDetail(word)
        wordDetailCall.enqueue(object : retrofit2.Callback<WordDetails> {
            override fun onResponse(call: Call<WordDetails>, response: Response<WordDetails>) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val wordDetail = response.body()!!
                    Log.d(TAG, "Food Details: $wordDetail")
                    wordDetailResultLiveDataList.postValue(wordDetail)
                }
            }

            override fun onFailure(call: Call<WordDetails>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getBMIDetails(age: String, weight: String, height: String) {
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getFitnessApiClient().create(WeatherApiInterface::class.java)
        bmiDetailCall =
            apiInterface.getBodyMassIndex(age.toDouble(), weight.toDouble(), height.toDouble())
        bmiDetailCall.enqueue(object : retrofit2.Callback<BodyMassIndexDetails> {
            override fun onResponse(
                call: Call<BodyMassIndexDetails>,
                response: Response<BodyMassIndexDetails>
            ) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val wordDetail = response.body()!!
                    Log.d(TAG, "Food Details: $wordDetail")
                    bodyMassIndexResultLiveDataList.postValue(wordDetail)
                }
            }

            override fun onFailure(call: Call<BodyMassIndexDetails>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }

    fun getUser(context: Context): String {
        dbHelper = DatabaseHelper(context)
        val checkBiQuery = dbHelper!!.ExecuteBiQuery("Select * from TokenDetails")
        return checkBiQuery.getString(2)
    }

    fun getAllProducts(context: Context) {
        val productList = mutableListOf<Products>()
        Completable.fromAction {
            dbHelper = DatabaseHelper(context)
            val productListCursor = dbHelper!!.ExecuteBiQuery("Select * from ProductMaster")
            if (!productListCursor.isNullOrEmpty()) {
                for (cursor in CursorUtils.iterate(productListCursor)) {
                    val product = Products()
                    product.productId = productListCursor.getInt(0)
                    product.productName = productListCursor.getString(1)
                    product.price = productListCursor.getInt(2)
                    product.quantity = productListCursor.getString(3)
                    product.discount = productListCursor.getInt(4)
                    productList.add(product)
                }
            }
        }
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Log.d(TAG, "On Complete of fg company names")
                    if (productList.size > 1) {
                        productListLiveDataList.postValue(productList)
                    } else {
                        productListLiveDataList.postValue(null)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "Error occurred in fetching FG list $e")
                    productListLiveDataList.postValue(null)
                }

            })

    }

    fun getCart(): LiveData<List<CartItem>> {
        if (cartLiveDataList.value == null) {
            initCart()
        }
        return cartLiveDataList
    }

    fun initCart() {
        cartLiveDataList.value = ArrayList<CartItem>()
        calculateTotal()
    }

    fun addItemToCart(products: Products): Boolean {
        if (cartLiveDataList.value == null) {
            initCart()
        }
        val cartItemList = mutableListOf<CartItem>()
        cartItemList.addAll(cartLiveDataList.value!!)

        for (cartItem in cartItemList) {
            if (cartItem.products.productId == products.productId) {
                if (cartItem.quantity == 5) {
                    return false
                }
                val index = cartItemList.indexOf(cartItem)
                cartItem.quantity = cartItem.quantity + 1
                cartItemList[index] = cartItem
                cartLiveDataList.value = cartItemList
                calculateTotal()
                return true
            }
        }

        val cartItem = CartItem(products, 1)
        cartItemList.add(cartItem)
        cartLiveDataList.value = cartItemList
        calculateTotal()
        return true
    }

    fun removeItem(cartItem: CartItem) {
        if (cartLiveDataList.value == null) {
            return
        }
        val cartItemList = mutableListOf<CartItem>()
        cartItemList.addAll(cartLiveDataList.value!!)
        cartItemList.remove(cartItem)
        cartLiveDataList.value = cartItemList
        calculateTotal()
    }

    fun changeQuantity(cartItem: CartItem, quantity: Int) {
        if (cartLiveDataList.value == null) {
            return
        }
        val cartItemList = mutableListOf<CartItem>()
        cartItemList.addAll(cartLiveDataList.value!!)
        val cartUpdateItem = CartItem(cartItem.products, quantity)
        cartItemList[cartItemList.indexOf(cartItem)] = cartUpdateItem
        cartLiveDataList.value = cartItemList
        calculateTotal()
    }

    fun getTotalPrice(): LiveData<Double> {
        if (totalLiveDataList.value == null) {
            totalLiveDataList.value = 0.0
        }
        return totalLiveDataList
    }

    private fun calculateTotal(){
        if (cartLiveDataList.value == null) {
            return
        }
        var total = 0.0
        val cartItemList = mutableListOf<CartItem>()
        cartItemList.addAll(cartLiveDataList.value!!)
        for (cartItem in cartItemList){
            total += if (cartItem.products.discount==0){
                cartItem.products.price * cartItem.quantity
            }else{
                val a: Int = cartItem.products.price * cartItem.quantity
                val b: Float = cartItem.products.discount.toFloat() / 100
                val c = (a * b).toInt()
                val d = a - c
                d
            }

        }
        totalLiveDataList.value = total
    }

    fun getIdealWeightDetails(gender: String, height: String) {
        val apiInterface: WeatherApiInterface =
            WeatherApiClient.getFitnessApiClient().create(WeatherApiInterface::class.java)
        idealWeightDetailCall = apiInterface.getIdealWeightDetails(gender, height.toDouble())
        idealWeightDetailCall.enqueue(object : retrofit2.Callback<IdealWeightDetails> {
            override fun onResponse(
                call: Call<IdealWeightDetails>,
                response: Response<IdealWeightDetails>
            ) {
                if (response.body() != null && response.isSuccessful && response.body() != null) {
                    val idealWeightDetail = response.body()!!
                    Log.d(TAG, "Food Details: $idealWeightDetail")
                    idealWeightResultLiveDataList.postValue(idealWeightDetail)
                }
            }

            override fun onFailure(call: Call<IdealWeightDetails>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
}