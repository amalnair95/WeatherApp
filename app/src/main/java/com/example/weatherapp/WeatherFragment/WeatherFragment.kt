package com.example.weatherapp.weatherFragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val fragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)
    private val TAG = WeatherFragment::class.java.simpleName
    private lateinit var weatherViewModel: WeatherViewModel
    private var gpsTracker: GPSTracker? = null
    private var address: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        weatherViewModel.resultLiveDataList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Response :$it")
            fragmentWeatherBinding.loadingProgressBar.visibility = View.GONE
            fragmentWeatherBinding.mainLayout.visibility = View.VISIBLE
            //fragmentWeatherBinding.testButton.visibility = View.GONE
          /*  if (it.summary == "Humid") {
                fragmentWeatherBinding.weatherIcon.setBackgroundResource(R.drawable.clearsky)
            } else {
                fragmentWeatherBinding.weatherIcon.setBackgroundResource(R.drawable.clearsky)
            }*/
            fragmentWeatherBinding.temperatureTextView.text = it.temperature
            //((Fahrenheit-32)*5)/9;
           /* fragmentWeatherBinding.summaryTextView.text = it.summary*/
            if (address != null) {
                fragmentWeatherBinding.address.text = address
            } else {
                fragmentWeatherBinding.address.visibility = View.GONE
            }

            fragmentWeatherBinding.apparentTempTextview.text = "${it.apparentTemperature} \u2103"
            fragmentWeatherBinding.humidityTextview.text = "${it.humidity} %"
            fragmentWeatherBinding.windSpeedTextview.text = "${it.windSpeed} km/h"
            fragmentWeatherBinding.minTempTextview.text = "${it.minTemp} \u2103"
            fragmentWeatherBinding.maxTempTextview.text = "${it.maxTemp} \u2103"
            fragmentWeatherBinding.cloudPrecipTextview.text = it.cloudPrecipitation
            if(!it.sunrise.isNullOrEmpty()){
                val date:Date = Date(it.sunrise.toInt()*1000L); // *1000 is to convert seconds to milliseconds
                val sdf:SimpleDateFormat  = SimpleDateFormat("h:mm a"); // the format of your date
                val sunriseTime = sdf.format(date)
                Log.e("sunrise Time", sunriseTime.toString())
                fragmentWeatherBinding.sunriseTimeTextview.text = sunriseTime.toString()
            }
            if(!it.sunset.isNullOrEmpty()){
                val date:Date = Date(it.sunset.toInt()*1000L) // *1000 is to convert seconds to milliseconds
                val sdf:SimpleDateFormat  = SimpleDateFormat("h:mm a") // the format of your date
                val sunsetTime = sdf.format(date)
                Log.e("sunset Time", sunsetTime.toString())
                fragmentWeatherBinding.sunsetTimeTextview.text = sunsetTime.toString()
            }


        })
    }


    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            weatherViewModel.getWeatherDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
            Navigation.findNavController(fragmentWeatherBinding.root).navigate(R.id.action_weather_to_category)
        }
    }

    private fun getData() {
        fragmentWeatherBinding.mainLayout.visibility = View.GONE
        if (CommonMethod.isNetworkConnected(requireContext())) {
            fragmentWeatherBinding.loadingProgressBar.visibility = View.VISIBLE
            getLocation()
        }else{
            Navigation.findNavController(fragmentWeatherBinding.root).navigate(R.id.action_weather_to_category)
        }
    }


    private fun init() {
        //CommonMethod.playBeep(requireContext())
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        getData()
       /* fragmentWeatherBinding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            fragmentWeatherBinding.swipeRefreshLayout.isRefreshing = false
        }*/
    }
}
