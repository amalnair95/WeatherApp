package com.example.weatherapp.foodFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentFoodDetailsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class FoodQualityDetailsFragment : Fragment(R.layout.fragment_food_details) {
    private val fragmentFoodDetailsBinding by viewBinding(FragmentFoodDetailsBinding::bind)
    private val TAG = FoodQualityDetailsFragment::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {

    }

    private fun init() {

        fragmentFoodDetailsBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentFoodDetailsBinding.root,requireActivity())
            false
        }

        fragmentFoodDetailsBinding.submitButton.setOnClickListener {
            Log.d(TAG,"Entered Text: ${fragmentFoodDetailsBinding.foodNameEditText.text.toString()}")
        }
    }
}