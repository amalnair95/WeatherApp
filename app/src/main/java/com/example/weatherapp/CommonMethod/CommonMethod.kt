package com.example.weatherapp.commonMethod

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.weatherapp.R
import com.example.weatherapp.models.Coordinates

class CommonMethod {
    companion object{
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        fun isNetworkConnected(context: Context): Boolean {
            return if (isNetworkAvailable(context)) {
                true
            } else {
                showAlert(context)
                false
            }
        }

        private fun showAlert(context: Context?) {
            val alertDialog = AlertDialog.Builder(context)

            //Setting Dialog Title
            alertDialog.setTitle(R.string.NetworkPermission)

            //Setting Dialog Message
            alertDialog.setMessage(R.string.NetworkConnection)

            //On pressing cancel button
            alertDialog.setNegativeButton(R.string.ok) { dialog, _ -> dialog.cancel() }
            alertDialog.show()
        }

        fun getLocation(context: Context) : Coordinates {
            var gpsTracker: GPSTracker? = null
            val coordinates=Coordinates()
            gpsTracker = GPSTracker(context)
            if (gpsTracker.isGPSTrackingEnabled) {
                coordinates.latitude = gpsTracker.getCurrentLatitude()
                coordinates.longitude = gpsTracker.getCurrentLongitude()
                coordinates.address = gpsTracker.getAddressLine(context).toString()
                val postalCode = gpsTracker.getPostalCode(context)
                val country = gpsTracker.getCountryName(context)
                println("Latitude:${coordinates.latitude} & Longitude:${coordinates.longitude}")
                //weatherViewModel.clearResultSet()
                //weatherViewModel.getWeatherDetail(latitude,longitude)

            }
            return coordinates
        }
    }
}