package com.example.weatherapp.loginFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databaseFiles.DatabaseHelper
import com.example.weatherapp.databinding.FragmentForgotPasswordBinding
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {
    private val fragmentForgotPasswordBinding by viewBinding(FragmentForgotPasswordBinding::bind)
    var resultContinuation: ForgotPasswordContinuation? = null
    private val TAG = ForgotPasswordFragment::class.java.simpleName
    private var dbHelper: DatabaseHelper?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {

        fragmentForgotPasswordBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentForgotPasswordBinding.root, requireActivity())
            false
        }

        dbHelper= DatabaseHelper(requireContext())
        fragmentForgotPasswordBinding.getCodeLayout.visibility = View.VISIBLE
        fragmentForgotPasswordBinding.getNewPasswordLayout.visibility = View.GONE
        val callback = object : ForgotPasswordHandler {
            override fun onSuccess() {
                Log.d(TAG, "Password changed successfully")
                val checkBiQuery= dbHelper!!.ExecuteBiQuery("UPDATE TokenDetails SET Password='${fragmentForgotPasswordBinding.newPasswordEditText.text.toString()}' where UserName='${fragmentForgotPasswordBinding.userNameEditText.text.toString()}'")
                Navigation.findNavController(fragmentForgotPasswordBinding.root).navigate(R.id.action_forgot_password_to_login)

            }

            override fun getResetCode(continuation: ForgotPasswordContinuation) {
                Log.d(TAG, "Inside getResetCode")
                val codeSentHere = continuation.parameters
                Log.d(TAG, "Code sent here:${codeSentHere.destination}")
                resultContinuation = continuation
                fragmentForgotPasswordBinding.getCodeLayout.visibility = View.GONE
                fragmentForgotPasswordBinding.getNewPasswordLayout.visibility = View.VISIBLE
            }

            override fun onFailure(exception: Exception?) {
                Log.d(TAG, "Password change failed ${exception?.localizedMessage}")
                CommonMethod.loadPopUp(exception?.localizedMessage.toString(),requireContext())
            }

        }
        fragmentForgotPasswordBinding.getCodeButton.setOnClickListener {
            val cognitoSettings = CognitoSettings(requireContext())
            val value = CommonMethod.getUserNameForAuthentication(fragmentForgotPasswordBinding.userNameEditText,requireActivity())
            val thisUser = cognitoSettings.userPool.getUser(value)
            Log.d(TAG, "calling forgot password to get confirmation code")
            thisUser.forgotPasswordInBackground(callback)
        }

        fragmentForgotPasswordBinding.resetPasswordButton.setOnClickListener {
            Log.d(TAG, "got code and password,setting continuation object")
            resultContinuation?.setPassword(fragmentForgotPasswordBinding.newPasswordEditText.text.toString())
            resultContinuation?.setVerificationCode(fragmentForgotPasswordBinding.codeEditText.text.toString())
            resultContinuation?.continueTask()
        }
    }
}