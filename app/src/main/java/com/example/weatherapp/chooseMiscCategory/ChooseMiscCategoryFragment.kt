package com.example.weatherapp.chooseMiscCategory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentMiscCategoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseMiscCategoryFragment:Fragment(R.layout.fragment_misc_category) {
    private val fragmentMiscCategoryBinding by viewBinding(FragmentMiscCategoryBinding::bind)
    private val TAG = ChooseMiscCategoryFragment::class.java.simpleName

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
        fragmentMiscCategoryBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentMiscCategoryBinding.root,requireActivity())
            false
        }

        fragmentMiscCategoryBinding.foodDetailsCardView.setOnClickListener {
            Navigation.findNavController(fragmentMiscCategoryBinding.root)
                .navigate(R.id.action_misc_cate_to_food_details)
            /* Navigation.findNavController(fragmentCategoryBinding.root)
                 .navigate(R.id.action_category_to_register)*/
        }
        fragmentMiscCategoryBinding.recipeDetailsCardView.setOnClickListener {
            Navigation.findNavController(fragmentMiscCategoryBinding.root)
                .navigate(R.id.action_misc_cate_to_food_recipe)
        }

        fragmentMiscCategoryBinding.dictionaryCardView.setOnClickListener {
            Navigation.findNavController(fragmentMiscCategoryBinding.root)
                .navigate(R.id.action_misc_cate_to_dictionary)
        }
    }
}