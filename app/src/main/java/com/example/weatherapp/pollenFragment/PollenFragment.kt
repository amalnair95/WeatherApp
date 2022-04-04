package com.example.weatherapp.pollenFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.databinding.FragmentAirBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class PollenFragment : Fragment(R.layout.fragment_air) {
    private val fragmentPollenBinding by viewBinding(FragmentAirBinding::bind)
    private val TAG = PollenFragment::class.java.simpleName
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
        weatherViewModel.pollenResultLiveDataList.observe(viewLifecycleOwner) {
            //println(it[0])
            if(it!=null) {
                val pollenData = it[0]
                fragmentPollenBinding.loadingProgressBar.visibility = View.GONE
                fragmentPollenBinding.pollenMainLayout.visibility = View.VISIBLE
                if (fragmentPollenBinding.pollenAddress.text != null) {
                    if (address!=null){
                        fragmentPollenBinding.pollenAddress.text = address
                    }else{
                        fragmentPollenBinding.pollenAddress.visibility = View.GONE
                    }
                } else {
                    fragmentPollenBinding.pollenAddress.visibility = View.GONE
                }

                fragmentPollenBinding.countGrassPollenTextview.text =
                    pollenData.countData.grassPollen.toString()
                fragmentPollenBinding.countTreePollenTextview.text =
                    pollenData.countData.treePollen.toString()
                fragmentPollenBinding.countWeedPollenTextview.text =
                    pollenData.countData.weedPollen.toString()
                fragmentPollenBinding.riskGrassPollenTextview.text =
                    pollenData.riskData.grassPollen.toString()
                fragmentPollenBinding.riskTreePollenTextview.text =
                    pollenData.riskData.treePollen.toString()
                fragmentPollenBinding.riskWeedPollenTextview.text =
                    pollenData.riskData.weedPollen.toString()
            }else{
                CommonMethod.loadPopUp("No Data Retrieved from API",requireContext())
            }
        }
    }

    private fun getData() {
        fragmentPollenBinding.pollenMainLayout.visibility = View.GONE
        fragmentPollenBinding.airMainLayout.visibility = View.GONE
        if (CommonMethod.isNetworkConnected(requireContext())) {
            fragmentPollenBinding.loadingProgressBar.visibility = View.VISIBLE
            getLocation()
        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        getData()
      /*  fragmentPollenBinding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            fragmentPollenBinding.swipeRefreshLayout.isRefreshing = false
        }*/
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            weatherViewModel.getPollenDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
            Navigation.findNavController(fragmentPollenBinding.root).navigate(R.id.action_pollen_to_category)
        }
    }


}