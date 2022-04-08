package com.example.weatherapp.foodFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentFoodRecipeBinding
import com.example.weatherapp.models.FoodDetails
import com.example.weatherapp.models.FoodRecipeDetails
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class FoodRecipeFragment : Fragment(R.layout.fragment_food_recipe) {
    private val fragmentFoodRecipeBinding by viewBinding(FragmentFoodRecipeBinding::bind)
    private val TAG = FoodRecipeFragment::class.java.simpleName
    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel
    // adapter
    private lateinit var foodRecipeDetailsAdapter: FoodRecipeDetailsAdapter
    // collections
    private val foodRecipeDetailsList: MutableList<FoodRecipeDetails> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        weatherViewModel.foodRecipeDetailResultLiveDataList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Food Recipe List $it")
            if (it.isEmpty()){
                CommonMethod.loadPopUp("Please Enter a Valid Food Item",requireContext())
                fragmentFoodRecipeBinding.loadingProgressBar.visibility = View.GONE
            }
            foodRecipeDetailsList.clear()
            foodRecipeDetailsList.addAll(it)
            foodRecipeDetailsAdapter = FoodRecipeDetailsAdapter(context, foodRecipeDetailsList)
            fragmentFoodRecipeBinding.itemRecyclerView.itemAnimator = DefaultItemAnimator()
            fragmentFoodRecipeBinding.itemRecyclerView.layoutManager = LinearLayoutManager(context)
            fragmentFoodRecipeBinding.itemRecyclerView.adapter = foodRecipeDetailsAdapter
            fragmentFoodRecipeBinding.loadingProgressBar.visibility = View.GONE
        })
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentFoodRecipeBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentFoodRecipeBinding.root, requireActivity())
            false
        }

        fragmentFoodRecipeBinding.submitButton.setOnClickListener {
            Log.d(TAG, "Entered Text: ${fragmentFoodRecipeBinding.foodNameEditText.text.toString()}")
            weatherViewModel.getFoodRecipeDetails(fragmentFoodRecipeBinding.foodNameEditText.text.toString())
        }

    }
}

