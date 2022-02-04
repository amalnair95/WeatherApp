package com.example.weatherapp.WeatherFragment

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.CommonMethod.GPSTracker
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val fragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)
    private val TAG = WeatherFragment::class.java.simpleName
    private lateinit var weatherViewModel: WeatherViewModel
    private var gpsTracker: GPSTracker? = null
    private var address:String?=null
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
            fragmentWeatherBinding.address.text=address
            fragmentWeatherBinding.apparentTempTextview.text="${it.apparentTemperature} \u2109"
            fragmentWeatherBinding.humidityTextview.text=it.humidity
            fragmentWeatherBinding.windSpeedTextview.text="${it.windSpeed} km/h"
            fragmentWeatherBinding.dewPointTextview.text=it.dewPoint
            fragmentWeatherBinding.visibilityTextview.text="${it.visibility} km"
            fragmentWeatherBinding.airPressureTextview.text="${it.pressure} hPa"
            fragmentWeatherBinding.cloudCoverTextview.text=it.cloudCover
            fragmentWeatherBinding.ozoneTextview.text=it.ozone
        })
    }


    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val latitude = gpsTracker!!.getCurrentLatitude()
            val longitude = gpsTracker!!.getCurrentLongitude()
            address = gpsTracker!!.getAddressLine(context)
            val postalCode = gpsTracker!!.getPostalCode(context)
            val country = gpsTracker!!.getCountryName(context)
            println("Latitude:$latitude & Longitude:$longitude")
            //weatherViewModel.clearResultSet()
            weatherViewModel.getWeatherDetail(latitude,longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun isNetworkConnected(): Boolean {
        return if (isNetworkAvailable()) {
            true
        } else {
            showAlert(context)
            false
        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentWeatherBinding.testButton.setOnClickListener {
            fragmentWeatherBinding.mainLayout.visibility=View.GONE
            fragmentWeatherBinding.loadingProgressBar.visibility=View.VISIBLE
            if (isNetworkConnected()) {
                getLocation()
            }
            //weatherViewModel.getWeatherDetail(12.9889055,77.574044)
        }
    }
}

fun showAlert(context: Context?) {
    val alertDialog = AlertDialog.Builder(context)

    //Setting Dialog Title
    alertDialog.setTitle(R.string.NetworkPermission)

    //Setting Dialog Message
    alertDialog.setMessage(R.string.NetworkConnection)

    /*  //On Pressing Setting button
      alertDialog.setPositiveButton(R.string.action_settings) { dialog, which ->
          val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
          context?.startActivity(intent)
      }*/

    //On pressing cancel button
    alertDialog.setNegativeButton(R.string.ok) { dialog, which -> dialog.cancel() }
    alertDialog.show()
}