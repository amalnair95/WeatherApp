package com.example.weatherapp.weatherFragment

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val fragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)
    private val TAG = WeatherFragment::class.java.simpleName
    private lateinit var weatherViewModel: WeatherViewModel
    private var gpsTracker: GPSTracker? = null
    private var address: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
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
            if (it.summary == "Humid") {
                fragmentWeatherBinding.weatherIcon.setBackgroundResource(R.drawable.clearsky)
            } else {
                fragmentWeatherBinding.weatherIcon.setBackgroundResource(R.drawable.clearsky)
            }
            fragmentWeatherBinding.temperatureTextView.text = it.temperature
            //((Fahrenheit-32)*5)/9;
            fragmentWeatherBinding.summaryTextView.text = it.summary
            if (address != null) {
                fragmentWeatherBinding.address.text = address
            } else {
                fragmentWeatherBinding.address.visibility = View.GONE
            }
            fragmentWeatherBinding.apparentTempTextview.text = "${it.apparentTemperature} \u2109"
            fragmentWeatherBinding.humidityTextview.text = it.humidity
            fragmentWeatherBinding.windSpeedTextview.text = "${it.windSpeed} km/h"
            fragmentWeatherBinding.dewPointTextview.text = it.dewPoint
            fragmentWeatherBinding.visibilityTextview.text = "${it.visibility} km"
            fragmentWeatherBinding.airPressureTextview.text = "${it.pressure} hPa"
            fragmentWeatherBinding.cloudCoverTextview.text = it.cloudCover
            fragmentWeatherBinding.ozoneTextview.text = it.ozone
        })
    }


    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            //weatherViewModel.clearResultSet()
            weatherViewModel.getWeatherDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }

    private fun getData() {
        fragmentWeatherBinding.mainLayout.visibility = View.GONE
        if (CommonMethod.isNetworkConnected(requireContext())) {
            fragmentWeatherBinding.loadingProgressBar.visibility = View.VISIBLE
            getLocation()
            //CommonMethod.getLocation(requireContext())
        }
    }


    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        getData()
        /*  fragmentWeatherBinding.testButton.setOnClickListener {
              fragmentWeatherBinding.mainLayout.visibility=View.GONE
              if (CommonMethod.isNetworkConnected(requireContext())) {
                  fragmentWeatherBinding.loadingProgressBar.visibility=View.VISIBLE
                  getLocation()
                  //CommonMethod.getLocation(requireContext())
              }
              //weatherViewModel.getWeatherDetail(12.9889055,77.574044)
          }*/
        fragmentWeatherBinding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            fragmentWeatherBinding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
