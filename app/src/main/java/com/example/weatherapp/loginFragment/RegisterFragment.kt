package com.example.weatherapp.loginFragment

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentRegisterBinding
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val fragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)

    private val TAG = RegisterFragment::class.java.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        fragmentRegisterBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentRegisterBinding.root,requireActivity())
            false
        }
        registerUser()
    }

    private fun registerUser() {

        val signUpCallBack=object:SignUpHandler{
            override fun onSuccess(user: CognitoUser?, signUpConfirmationState: Boolean, cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
                //sign up was successfull
                Log.d(TAG,"sign up success..is Confirmed:$signUpConfirmationState")
                //check if user needs to be confirmed
                if(!signUpConfirmationState){
                    Log.d(TAG,"sign up success..not Confirmed, verification code sent to :${cognitoUserCodeDeliveryDetails.destination}")
                }else{
                    Log.d(TAG,"sign up success..confirmed")
                }
                Navigation.findNavController(fragmentRegisterBinding.root).navigate(R.id.action_register_to_verification)
            }

            override fun onFailure(exception: Exception) {
                Log.d(TAG,"sign up failure: ${exception.localizedMessage}")
                Log.d(TAG,"error message: ${exception.message.toString().substringBefore("(")}")

                CommonMethod.loadPopUp(exception.message.toString().substringBefore("("),requireContext())
            }

        }

        val userAttributes = CognitoUserAttributes()
        fragmentRegisterBinding.registerButton.setOnClickListener {
            //userAttributes.attributes["phone_number"] = fragmentRegisterBinding.phoneEditText.text.toString()
            //userAttributes.attributes["custom:land_owner_no."] = fragmentRegisterBinding.phoneEditText.text.toString()
            userAttributes.attributes["given_name"]=fragmentRegisterBinding.nameEditText.text.toString()
            userAttributes.attributes["family_name"]=fragmentRegisterBinding.lastNameEditText.text.toString()
            //userAttributes.attributes["custom:land_owner_name"]=fragmentRegisterBinding.nameEditText.text.toString()
            //userAttributes.attributes["email"] = fragmentRegisterBinding.emailEditText.text.toString()
            val value = CommonMethod.getUserNameForAuthentication(fragmentRegisterBinding.userNameEditText,requireActivity())

            val cognitoSettings=CognitoSettings(requireContext())
            cognitoSettings.userPool.signUpInBackground(value,fragmentRegisterBinding.passwordEditText.text.toString(),userAttributes,null,signUpCallBack)
        }
    }
}