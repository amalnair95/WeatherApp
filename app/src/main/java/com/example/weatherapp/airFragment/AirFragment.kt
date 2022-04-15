package com.example.weatherapp.airFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        weatherViewModel.airResultLiveDataList.observe(viewLifecycleOwner) {
            fragmentAirBinding.loadingProgressBar.visibility = View.GONE
            fragmentAirBinding.airMainLayout.visibility = View.VISIBLE
            println("data $it")
            val airDetails = it
            if (fragmentAirBinding.address.text != null) {
                if(address!=null){
                    fragmentAirBinding.address.text = address
                }else{
                    fragmentAirBinding.address.visibility = View.GONE
                }
            } else {
                fragmentAirBinding.address.visibility = View.GONE
            }
            fragmentAirBinding.carbonMonoxideTextview.text = "${airDetails.co.concentration.toString()} ppm"
            fragmentAirBinding.nitrogenTextview.text = "${airDetails.no2.concentration.toString()} ppb"
            fragmentAirBinding.ozoneTextview.text = "${airDetails.o3.concentration.toString()} ppb"
            fragmentAirBinding.pm10Textview.text = "${ airDetails.pm10.concentration.toString() } ug/m3"
            fragmentAirBinding.pm25Textview.text = "${airDetails.pm25.concentration.toString()} ug/m3"
            fragmentAirBinding.sulphurTextview.text = "${airDetails.so2.concentration.toString()} ppb"
            fragmentAirBinding.airQualityTextview.text = airDetails.aqi.toString()

        }
    }

    private fun getData() {
        fragmentAirBinding.airMainLayout.visibility = View.GONE
        fragmentAirBinding.pollenMainLayout.visibility = View.GONE
        if (CommonMethod.isNetworkConnected(requireContext())) {
            fragmentAirBinding.loadingProgressBar.visibility = View.VISIBLE
            getLocation()
        }else{
            Navigation.findNavController(fragmentAirBinding.root).navigate(R.id.action_air_to_category)
        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        getData()
        /*fragmentAirBinding.testButton.setOnClickListener {
            fragmentAirBinding.loadingProgressBar.visibility=View.VISIBLE
            fragmentAirBinding.airMainLayout.visibility=View.GONE
            fragmentAirBinding.pollenMainLayout.visibility=View.GONE
            getLocation()
        }*/
  /*      fragmentAirBinding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            fragmentAirBinding.swipeRefreshLayout.isRefreshing = false
        }*/
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
            Navigation.findNavController(fragmentAirBinding.root).navigate(R.id.action_air_to_category)
        }
    }
}