package com.example.weatherapp.splashScreenFragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databaseFiles.DatabaseHelper
import com.example.weatherapp.databinding.FragmentSplashBinding
import com.example.weatherapp.isNullOrEmpty
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SplashScreenFragment : Fragment(R.layout.fragment_splash) {

    private val fragmentSplashBinding by viewBinding(FragmentSplashBinding::bind)
    private val TAG = SplashScreenFragment::class.java.simpleName


    lateinit var imageAnim: Animation
    private var dbHelper: DatabaseHelper?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        dbHelper= DatabaseHelper(requireContext())
        /*fragmentSplashBinding.splashLogoImageView.visibility=View.VISIBLE
        fragmentSplashBinding.mainLayout.visibility=View.GONE*/
        imageAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.image_animation)
        fragmentSplashBinding.splashLogoImageView.animation=imageAnim
        Handler().postDelayed({
            fragmentSplashBinding.splashLogoImageView.visibility=View.GONE
            val checkBiQuery= dbHelper!!.ExecuteBiQuery("Select * from TokenDetails")
            if (checkBiQuery?.getString(1) == null){
                Log.d(TAG,"No data")
                Navigation.findNavController(fragmentSplashBinding.root)
                    .navigate(R.id.action_splash_to_selection)
            }else{
                var value=""
                Log.d(TAG,"data ${checkBiQuery.columnCount}")
                if(!checkBiQuery.isNullOrEmpty()){
                    value=checkBiQuery.getString(2)
                }
                val cognitoSettings = CognitoSettings(requireContext())
                val thisUser = cognitoSettings.userPool.getUser(value)
                Log.d(TAG, "Login Button Clicked")
                //thisUser.getSessionInBackground(authenticationHandler)
                val args = Bundle()
                args.putString("@USERNAME", value)
                args.putString("@PROGRESSBAR", "50")
                Navigation.findNavController(fragmentSplashBinding.root).navigate(R.id.action_splash_to_category,args)
            }

        },3000)

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
       // (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}