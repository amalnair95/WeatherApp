package com.example.weatherapp.farmerDetailsFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.commonMethod.GPSTracker
import com.example.weatherapp.databinding.FragmentFarmerDetailsBinding
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class FarmerDetailsFragment:Fragment(R.layout.fragment_farmer_details) {
    private val fragmentFarmerDetailsBinding by viewBinding(FragmentFarmerDetailsBinding::bind)
    var userReceived: String=""
    var percentCompleted: String=""
    private var gpsTracker: GPSTracker? = null
    private var address: String? = null
    private val TAG = FarmerDetailsFragment::class.java.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        val bundle = arguments
        if (bundle != null) {
            userReceived = bundle.getString("@USERNAME").toString()
            percentCompleted=bundle.getString("@PROGRESSBAR").toString()
            Log.d(TAG,"UserName:$userReceived")
        }

        val callback=object : UpdateAttributesHandler {
            override fun onSuccess(attributesVerificationList: MutableList<CognitoUserCodeDeliveryDetails>?) {
                Log.d(TAG,"attributes List $attributesVerificationList")
                val args = Bundle()
                args.putString("@USERNAME", userReceived)
                args.putString("@PROGRESSBAR", percentCompleted)
                Navigation.findNavController(fragmentFarmerDetailsBinding.root).navigate(R.id.action_farmer_details_to_category, args)
            }

            override fun onFailure(exception:Exception?) {
                Log.d(TAG,"attributes List ${exception?.localizedMessage}")
            }

        }
      /*  if (CommonMethod.isNetworkConnected(requireContext())) {
            getLocation()
        }*/
        fragmentFarmerDetailsBinding.buttonProceed.setOnClickListener {
            val cognitoSettings = CognitoSettings(requireContext())
            val userAttributes = CognitoUserAttributes()
            val thisUser = cognitoSettings.userPool.getUser(userReceived)
            percentCompleted="75"
            Log.d(TAG, "Proceed Button Clicked")
            userAttributes.attributes["custom:land_owner_name"]=fragmentFarmerDetailsBinding.farmerNameEditText.text.toString()
            userAttributes.attributes["custom:land_area"]=fragmentFarmerDetailsBinding.farmerLandAreaEdittext.text.toString()
            userAttributes.attributes["custom:farmer_address"]=fragmentFarmerDetailsBinding.farmerAddressEdittext.text.toString()

            thisUser.updateAttributesInBackground(userAttributes,callback)
        }

        fragmentFarmerDetailsBinding.buttonSkip.setOnClickListener {
            /*val args = Bundle()
            args.putString("@USERNAME", userReceived)
            args.putString("@PROGRESSBAR", percentCompleted)*/
            Log.d(TAG, "Proceed Button Clicked")
            val cognitoSettings = CognitoSettings(requireContext())
            val userAttributes = CognitoUserAttributes()
            val thisUser = cognitoSettings.userPool.getUser(userReceived)
            userAttributes.attributes["custom:land_owner_name"]=fragmentFarmerDetailsBinding.farmerNameEditText.text.toString()
            userAttributes.attributes["custom:land_area"]=fragmentFarmerDetailsBinding.farmerLandAreaEdittext.text.toString()
            userAttributes.attributes["custom:farmer_address"]=fragmentFarmerDetailsBinding.farmerAddressEdittext.text.toString()
            thisUser.updateAttributesInBackground(userAttributes,callback)
            //Navigation.findNavController(fragmentFarmerDetailsBinding.root).navigate(R.id.action_farmer_details_to_category, args)
        }
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker?.isGPSTrackingEnabled!!) {
            val coordinates = CommonMethod.getLocation(requireContext())
            address = coordinates.address

        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }
}