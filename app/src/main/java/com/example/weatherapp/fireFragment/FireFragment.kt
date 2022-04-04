package com.example.weatherapp.fireFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.airFragment.AirFragment
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.databinding.FragmentAirBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception
import kotlin.math.roundToInt

class FireFragment : Fragment(R.layout.fragment_air) {

    private val fragmentFireBinding by viewBinding(FragmentAirBinding::bind)
    private val TAG = FireFragment::class.java.simpleName
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

        weatherViewModel.fireResultLiveDataList.observe(viewLifecycleOwner) {
            fragmentFireBinding.loadingProgressBar.visibility = View.GONE
            println("data $it")
            try{
                if(it!=null){
                    fragmentFireBinding.fireMainLayout.visibility = View.VISIBLE
                    val fireDetails = it[0]
                    if (fragmentFireBinding.fireAddress.text != null) {
                        fragmentFireBinding.fireAddress.text = address
                    } else {
                        fragmentFireBinding.fireAddress.visibility = View.GONE
                    }
                    if (fireDetails.confidence != null) {
                        fragmentFireBinding.confidenceLayout.visibility = View.VISIBLE
                        fragmentFireBinding.fireConfidenceTextView.text = fireDetails.confidence
                    }else{
                        fragmentFireBinding.confidenceLayout.visibility = View.GONE
                    }
                    if (fireDetails.frp != null) {
                        fragmentFireBinding.frpLayout.visibility = View.VISIBLE
                        fragmentFireBinding.frpTextView.text = "${fireDetails.frp.toString()} MW"
                    }else{
                        fragmentFireBinding.frpLayout.visibility = View.GONE
                    }
                    if (fireDetails.dayNight != null) {
                        fragmentFireBinding.dayNightLayout.visibility = View.VISIBLE
                        fragmentFireBinding.dayNightTextView.text = fireDetails.dayNight
                    } else {
                        fragmentFireBinding.dayNightLayout.visibility = View.GONE
                    }
                    if (fireDetails.distance != null) {
                        fragmentFireBinding.fireDistanceLayout.visibility = View.VISIBLE
                        fragmentFireBinding.distanceTextView.text = "${fireDetails.distance.roundToInt().toString()} km"
                    }else{
                        fragmentFireBinding.fireDistanceLayout.visibility = View.GONE
                    }

                }else{
                    CommonMethod.loadPopUp("No Data Retrieved from API",requireContext())
                }
            }catch (e:Exception){
                Log.d(TAG,"Error Occurred:$e ")
                CommonMethod.loadPopUp("No Data Retrieved from API",requireContext())
            }

        }
    }

    private fun getData() {
        fragmentFireBinding.airMainLayout.visibility = View.GONE
        fragmentFireBinding.pollenMainLayout.visibility = View.GONE
        fragmentFireBinding.soilMainLayout.visibility = View.GONE
        fragmentFireBinding.fireMainLayout.visibility = View.GONE
        if (CommonMethod.isNetworkConnected(requireContext())) {
            fragmentFireBinding.loadingProgressBar.visibility = View.VISIBLE
            getLocation()
        }
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        getData()
       /* fragmentFireBinding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            fragmentFireBinding.swipeRefreshLayout.isRefreshing = false
        }*/
        /* fragmentFireBinding.testButton.setOnClickListener {
             fragmentFireBinding.loadingProgressBar.visibility=View.VISIBLE
             fragmentFireBinding.airMainLayout.visibility=View.GONE
             fragmentFireBinding.pollenMainLayout.visibility=View.GONE
             fragmentFireBinding.soilMainLayout.visibility=View.GONE
             fragmentFireBinding.fireMainLayout.visibility=View.GONE
             getLocation()
         }*/
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address
            //weatherViewModel.clearResultSet()
            weatherViewModel.getFireDetail(coordinates.latitude, coordinates.longitude)

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }
}