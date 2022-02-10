package com.example.weatherapp.chooseCategory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCategoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseCategoryFragment:Fragment(R.layout.fragment_category) {

    private val fragmentCategoryBinding by viewBinding(FragmentCategoryBinding::bind)
    private val TAG = ChooseCategoryFragment::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {

    }

    private fun init() {
        fragmentCategoryBinding.weatherCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_weather)
        }
        fragmentCategoryBinding.airCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_air)
        }
        fragmentCategoryBinding.pollenCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_pollen)
        }
        fragmentCategoryBinding.soilCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_soil)
        }

        fragmentCategoryBinding.fireCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_fire)
        }
    }


}