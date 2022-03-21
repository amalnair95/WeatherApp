package com.example.weatherapp.splashScreenFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentSelectionBinding
import com.example.weatherapp.databinding.FragmentSplashBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SelectionFragment:Fragment(R.layout.fragment_selection) {

    private val fragmentSelectionBinding by viewBinding(FragmentSelectionBinding::bind)
    private val TAG = SelectionFragment::class.java.simpleName


    lateinit var imageAnim: Animation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        fragmentSelectionBinding.loginButton.setOnClickListener {
            Navigation.findNavController(fragmentSelectionBinding.root).navigate(R.id.action_selection_to_login)
        }
        fragmentSelectionBinding.signUpButton.setOnClickListener {
            Navigation.findNavController(fragmentSelectionBinding.root).navigate(R.id.action_selection_to_signup)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}