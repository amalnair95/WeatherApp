package com.example.weatherapp.airFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.databinding.FragmentAirBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class AirFragment : Fragment(R.layout.fragment_air) {
    private val fragmentAirBinding by viewBinding(FragmentAirBinding::bind)
    private val TAG = AirFragment::class.java.simpleName
    private var gpsTracker: GPSTracker? = null
    private var address: String? = null
    private lateinit var weatherViewModel: WeatherViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        weatherViewModel.airResultLiveDataList.observe(viewLifecycleOwner) {
            fragmentAirBinding.loadingProgressBar.visibility = View.GONE
            fragmentAirBinding.mainLayout.visibility = View.VISIBLE
            println("data $it")
            val airDetails = it[0]
            fragmentAirBinding.address.text=address
            fragmentAirBinding.carbonMonoxideTextview.text= airDetails.carbonMonoxide.toString()
            fragmentAirBinding.nitrogenTextview.text=airDetails.nitrogenOxide.toString()
            fragmentAirBinding.ozoneTextview.text=airDetails.ozone.toString()
            fragmentAirBinding.pm10Textview.text=airDetails.pM10.toString()
            fragmentAirBinding.pm25Textview.text=airDetails.pM25.toString()
            fragmentAirBinding.sulphurTextview.text=airDetails.sulphurDioxide.toString()
            fragmentAirBinding.airQualityTextview.text=airDetails.airQualityIndex.toString()

        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentAirBinding.airTestButton.setOnClickListener {
            fragmentAirBinding.loadingProgressBar.visibility=View.VISIBLE
            fragmentAirBinding.mainLayout.visibility=View.GONE
            getLocation()
        }
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            //weatherViewModel.clearResultSet()
            weatherViewModel.getAirDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }
}