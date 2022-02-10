package com.example.weatherapp.pollenFragment

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

class PollenFragment:Fragment(R.layout.fragment_air) {
    private val fragmentPollenBinding by viewBinding(FragmentAirBinding::bind)
    private val TAG = PollenFragment::class.java.simpleName
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
        weatherViewModel.pollenResultLiveDataList.observe(viewLifecycleOwner) {
        //println(it[0])
        val pollenData =it[0]
            fragmentPollenBinding.loadingProgressBar.visibility = View.GONE
            fragmentPollenBinding.pollenMainLayout.visibility = View.VISIBLE
            fragmentPollenBinding.pollenAddress.text=address
            fragmentPollenBinding.countGrassPollenTextview.text=pollenData.countData.grassPollen.toString()
            fragmentPollenBinding.countTreePollenTextview.text=pollenData.countData.treePollen.toString()
            fragmentPollenBinding.countWeedPollenTextview.text=pollenData.countData.weedPollen.toString()
            fragmentPollenBinding.riskGrassPollenTextview.text=pollenData.riskData.grassPollen.toString()
            fragmentPollenBinding.riskTreePollenTextview.text=pollenData.riskData.treePollen.toString()
            fragmentPollenBinding.riskWeedPollenTextview.text=pollenData.riskData.weedPollen.toString()
        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentPollenBinding.testButton.setOnClickListener {
            fragmentPollenBinding.loadingProgressBar.visibility=View.VISIBLE
            fragmentPollenBinding.pollenMainLayout.visibility=View.GONE
            fragmentPollenBinding.airMainLayout.visibility=View.GONE
            getLocation()
        }
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            //weatherViewModel.clearResultSet()
            weatherViewModel.getPollenDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }


}