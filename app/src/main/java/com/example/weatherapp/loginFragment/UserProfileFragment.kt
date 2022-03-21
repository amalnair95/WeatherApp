package com.example.weatherapp.loginFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentUserProfileBinding
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class UserProfileFragment:Fragment(R.layout.fragment_user_profile) {
    private val fragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)
    var userReceived: String=""
    var percentCompleted: String=""
    private val TAG = UserProfileFragment::class.java.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun init() {
        val bundle = arguments
        if (bundle != null) {
            userReceived = bundle.getString("@USERNAME").toString()
            percentCompleted = bundle.getString("@PROGRESSBAR").toString()
            Log.d(TAG,"UserName:$userReceived")
        }
        getUserDetails()
    }

    private fun getUserDetails() {
        val userDetailsHandler = object: GetDetailsHandler {
            override fun onSuccess(cognitoUserDetails: CognitoUserDetails?) {
                Log.d(TAG,"User Details: ${cognitoUserDetails?.attributes?.attributes}")
                val userDetails=cognitoUserDetails?.attributes?.attributes
                if (!userDetails.isNullOrEmpty()){
                    fragmentUserProfileBinding.percentCompletedTextView.text="Profile is $percentCompleted% completed"
                    fragmentUserProfileBinding.userNameTextView.text=userDetails["given_name"]
                    fragmentUserProfileBinding.emailTextView.text= userDetails["email"]
                    fragmentUserProfileBinding.phoneNumberTextView.text=userDetails["phone_number"]
                    fragmentUserProfileBinding.fullNameTextView.text=userDetails["custom:land_owner_name"]
                    fragmentUserProfileBinding.landAreaTextView.text=userDetails["custom:land_area"]
                    fragmentUserProfileBinding.addressTextView.text=userDetails["custom:farmer_address"]
                }
            }

            override fun onFailure(exception: Exception?) {
                Log.d(TAG,"sign up failure: ${exception?.localizedMessage}")
                CommonMethod.loadPopUp(exception?.message.toString(),requireContext())
            }

        }
        val cognitoSettings = CognitoSettings(requireContext())
        val thisUser = cognitoSettings.userPool.getUser(userReceived)
        Log.d(TAG,"Login Button Clicked")
        thisUser.getDetailsInBackground(userDetailsHandler)
    }


}