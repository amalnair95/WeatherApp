package com.example.weatherapp.loginFragment

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentVerifyBinding
import com.example.weatherapp.models.CognitoSettings
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception

class VerificationFragment:Fragment(R.layout.fragment_verify) {
    private val fragmentVerifyBinding by viewBinding(FragmentVerifyBinding::bind)

    private val TAG = VerificationFragment::class.java.simpleName
    private lateinit var verificationViewModel: VerificationViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        verificationViewModel.mutableResultData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"result:$it")
            //CommonMethod.loadPopUp(it!!,requireContext())
            Navigation.findNavController(fragmentVerifyBinding.root).navigate(R.id.action_verification_to_login)
        })

        verificationViewModel.mutableResendCodeData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"result:$it")
            CommonMethod.loadPopUp(it!!,requireContext())

        })
    }

    private fun init() {
        verificationViewModel = ViewModelProvider(requireActivity())[VerificationViewModel::class.java]
        verificationViewModel.clearResultSet()
        fragmentVerifyBinding.verifyButton.setOnClickListener {
            verifyUser()
        }
        fragmentVerifyBinding.resendCodeTextView.setOnClickListener {
            if (fragmentVerifyBinding.userNameEditText.text.toString().isNotEmpty()){
                resendCode()
            }else{
                CommonMethod.loadPopUp("Please Enter Username",requireContext())
            }

        }
    }

    private fun verifyUser() {
        verificationViewModel.getConfirmationCallback(requireContext(),fragmentVerifyBinding.verifyCodeEditText.text.toString(),fragmentVerifyBinding.userNameEditText.text.toString())
    }

    private fun resendCode(){
        val cognitoSettings=CognitoSettings(requireContext())
        val thisUser = cognitoSettings.userPool.getUser(fragmentVerifyBinding.userNameEditText.text.toString())
        verificationViewModel.resendVerificationCode(thisUser)

    }
}