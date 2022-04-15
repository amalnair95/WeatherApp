package com.example.weatherapp.foodFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentFoodDetailsBinding
import com.example.weatherapp.models.FoodDetails
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class FoodQualityDetailsFragment : Fragment(R.layout.fragment_food_details) {
    private val fragmentFoodDetailsBinding by viewBinding(FragmentFoodDetailsBinding::bind)
    private val TAG = FoodQualityDetailsFragment::class.java.simpleName
    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel
    // adapter
    private lateinit var foodDetailsAdapter: FoodDetailsAdapter
    // collections
    private val foodDetailsList: MutableList<FoodDetails> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        weatherViewModel.foodDetailResultLiveDataList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Food List $it")
            if (it.isEmpty()){
                CommonMethod.loadPopUp("Please Enter a Valid Food Item",requireContext())
                fragmentFoodDetailsBinding.loadingProgressBar.visibility = View.GONE
            }
            foodDetailsList.clear()
            foodDetailsList.addAll(it)
            foodDetailsAdapter = FoodDetailsAdapter(context, foodDetailsList)
            fragmentFoodDetailsBinding.itemRecyclerView.itemAnimator = DefaultItemAnimator()
            fragmentFoodDetailsBinding.itemRecyclerView.layoutManager = LinearLayoutManager(context)
            fragmentFoodDetailsBinding.itemRecyclerView.adapter = foodDetailsAdapter
            fragmentFoodDetailsBinding.loadingProgressBar.visibility = View.GONE

        })
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentFoodDetailsBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentFoodDetailsBinding.root, requireActivity())
            false
        }

        fragmentFoodDetailsBinding.submitButton.setOnClickListener {
            Log.d(TAG, "Entered Text: ${fragmentFoodDetailsBinding.foodNameEditText.text.toString()}")
            fragmentFoodDetailsBinding.loadingProgressBar.visibility = View.VISIBLE
            if (CommonMethod.isNetworkConnected(requireContext())) {
                weatherViewModel.getFoodDetails(fragmentFoodDetailsBinding.foodNameEditText.text.toString())
            }else{
                Navigation.findNavController(fragmentFoodDetailsBinding.root).navigate(R.id.action_food_details_to_category)
            }

        }
    }
}