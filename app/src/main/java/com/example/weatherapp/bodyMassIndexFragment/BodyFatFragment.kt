package com.example.weatherapp.bodyMassIndexFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentBodyFatBinding
import com.example.weatherapp.databinding.FragmentBodyMassIndexBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class BodyFatFragment:Fragment(R.layout.fragment_body_fat) {
    private val fragmentBodyFatBinding by viewBinding(FragmentBodyFatBinding::bind)
    private val TAG = BodyFatFragment::class.java.simpleName

    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //CommonMethod.backButtonCode(view)
        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setObservers() {

    }
    private fun init() {

    }
}