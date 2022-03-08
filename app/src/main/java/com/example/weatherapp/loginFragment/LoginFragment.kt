package com.example.weatherapp.loginFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentLoginBinding
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception

class LoginFragment:Fragment(R.layout.fragment_login) {
    private val fragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    private val TAG = LoginFragment::class.java.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        init()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun init() {
        val authenticationHandler = object : AuthenticationHandler{
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
               Log.d(TAG,"Login Successful")
                Navigation.findNavController(fragmentLoginBinding.root).navigate(R.id.action_login_to_category)
            }

            override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation, userId: String?) {
                Log.d(TAG,"inside getAuthenticationDetails")
                val authenticationDetails = AuthenticationDetails(userId,fragmentLoginBinding.passwordEditText.text.toString(),null)
                authenticationContinuation.setAuthenticationDetails(authenticationDetails)
                authenticationContinuation.continueTask()
            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
                Log.d(TAG,"inside getMFACode")
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                Log.d(TAG,"inside authenticationChallenge")
            }

            override fun onFailure(exception: Exception?) {
                Log.d(TAG,"Login Failed:${exception?.localizedMessage}")
                CommonMethod.loadPopUp(exception?.localizedMessage.toString(),requireContext())
            }

        }
        fragmentLoginBinding.loginButton.setOnClickListener {
            val cognitoSettings = CognitoSettings(requireContext())
            val thisUser = cognitoSettings.userPool.getUser(fragmentLoginBinding.userNameEditText.text.toString())
            Log.d(TAG,"Login Button Clicked")
            thisUser.getSessionInBackground(authenticationHandler)
        }

        fragmentLoginBinding.signUpButton.setOnClickListener {
            Navigation.findNavController(fragmentLoginBinding.root).navigate(R.id.action_login_to_register)
        }

        fragmentLoginBinding.forgotPasswordTextView.setOnClickListener {
            Navigation.findNavController(fragmentLoginBinding.root).navigate(R.id.action_login_to_forgot_password)
        }
    }
}