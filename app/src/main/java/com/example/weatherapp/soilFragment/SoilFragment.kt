package com.example.weatherapp.soilFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.databinding.FragmentAirBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlin.math.roundToInt

class SoilFragment : Fragment(R.layout.fragment_air) {

    private val fragmentSoilBinding by viewBinding(FragmentAirBinding::bind)
    private val TAG = SoilFragment::class.java.simpleName
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
        weatherViewModel.soilResultLiveDataList.observe(viewLifecycleOwner) {
            fragmentSoilBinding.loadingProgressBar.visibility = View.GONE
            fragmentSoilBinding.soilMainLayout.visibility = View.VISIBLE
            println("data $it")
            val soilDetails = it[0]
            if (fragmentSoilBinding.soilAddress.text != null) {
                fragmentSoilBinding.soilAddress.text = address
            } else {
                fragmentSoilBinding.soilAddress.visibility = View.GONE
            }
            fragmentSoilBinding.soilTempTextView.text =
                "${soilDetails.soilTemp.roundToInt().toString()} \u2103"
            fragmentSoilBinding.soilMoistTextView.text =
                "${soilDetails.soilMoist.roundToInt().toString()}%"

        }
    }

    private fun getData() {
        fragmentSoilBinding.airMainLayout.visibility = View.GONE
        fragmentSoilBinding.pollenMainLayout.visibility = View.GONE
        fragmentSoilBinding.soilMainLayout.visibility = View.GONE
        if (CommonMethod.isNetworkConnected(requireContext())) {
            fragmentSoilBinding.loadingProgressBar.visibility = View.VISIBLE
            getLocation()
        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        getData()
        fragmentSoilBinding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            fragmentSoilBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            weatherViewModel.getSoilDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }
}