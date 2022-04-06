package com.example.weatherapp.foodFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentFoodRecipeBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class FoodRecipeFragment : Fragment(R.layout.fragment_food_recipe) {
    private val fragmentFoodRecipeBinding by viewBinding(FragmentFoodRecipeBinding::bind)
    private val TAG = FoodRecipeFragment::class.java.simpleName

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
        fragmentFoodRecipeBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentFoodRecipeBinding.root, requireActivity())
            false
        }

        fragmentFoodRecipeBinding.submitButton.setOnClickListener {
            Log.d(
                TAG,
                "Entered Text: ${fragmentFoodRecipeBinding.foodNameEditText.text.toString()}"
            )
        }
    }
}

