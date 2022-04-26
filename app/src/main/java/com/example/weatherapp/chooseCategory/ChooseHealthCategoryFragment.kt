package com.example.weatherapp.chooseCategory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentHealthCategoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseHealthCategoryFragment:Fragment(R.layout.fragment_health_category) {
    private val fragmentHealthCategoryBinding by viewBinding(FragmentHealthCategoryBinding::bind)
    private val TAG = ChooseHealthCategoryFragment::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {

    }

    private fun init() {
        fragmentHealthCategoryBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentHealthCategoryBinding.root,requireActivity())
            false
        }
        fragmentHealthCategoryBinding.bmiCardView.setOnClickListener {
            Navigation.findNavController(fragmentHealthCategoryBinding.root)
                .navigate(R.id.action_category_to_bmi)
        }
        fragmentHealthCategoryBinding.idealWeightCardView.setOnClickListener {
            Navigation.findNavController(fragmentHealthCategoryBinding.root)
                .navigate(R.id.action_category_to_ideal_weight)
        }
        fragmentHealthCategoryBinding.bodyFatCardView.setOnClickListener {
            Navigation.findNavController(fragmentHealthCategoryBinding.root)
                .navigate(R.id.action_category_to_body_fat)
        }

    }
}