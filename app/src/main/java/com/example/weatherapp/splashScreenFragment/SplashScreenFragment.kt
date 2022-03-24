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
import com.example.weatherapp.databinding.FragmentSplashBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SplashScreenFragment : Fragment(R.layout.fragment_splash) {

    private val fragmentSplashBinding by viewBinding(FragmentSplashBinding::bind)
    private val TAG = SplashScreenFragment::class.java.simpleName


    lateinit var imageAnim: Animation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        /*fragmentSplashBinding.splashLogoImageView.visibility=View.VISIBLE
        fragmentSplashBinding.mainLayout.visibility=View.GONE*/
        imageAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.image_animation)
        fragmentSplashBinding.splashLogoImageView.animation=imageAnim

        Handler().postDelayed({
            fragmentSplashBinding.splashLogoImageView.visibility=View.GONE
            Navigation.findNavController(fragmentSplashBinding.root)
                .navigate(R.id.action_splash_to_selection)
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