package com.example.weatherapp.bodyMassIndexFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentIdealWeightBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class IdealWeightFragment : Fragment(R.layout.fragment_ideal_weight) {
    private val fragmentIdealWeightBinding by viewBinding(FragmentIdealWeightBinding::bind)
    private val TAG = IdealWeightFragment::class.java.simpleName
    var genderString = ""

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
        weatherViewModel.idealWeightResultLiveDataList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Ideal weight details $it")
            if (it != null) {
                fragmentIdealWeightBinding.loadingProgressBar.visibility = View.GONE
                fragmentIdealWeightBinding.idealWeightDetailsLayout.visibility = View.VISIBLE
                fragmentIdealWeightBinding.devineMethodTextview.text=it.results.devine+" kg"
                fragmentIdealWeightBinding.millerMethodTextview.text=it.results.miller+" kg"
                fragmentIdealWeightBinding.robinsonMethodTextview.text=it.results.robinson+" kg"
                fragmentIdealWeightBinding.hamwiMethodTextview.text=it.results.hamwi+" kg"
            }else{
                CommonMethod.loadPopUp("Failed to retrieve data", requireContext())
                fragmentIdealWeightBinding.idealWeightDetailsLayout.visibility = View.VISIBLE
                fragmentIdealWeightBinding.loadingProgressBar.visibility = View.GONE
            }

        })
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentIdealWeightBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentIdealWeightBinding.root, requireActivity())
            false
        }
        fragmentIdealWeightBinding.genderRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val rb = radioGroup.findViewById(i) as RadioButton
            Log.d(TAG, "Selected Gender ${rb.text.toString().lowercase()}")
            genderString = rb.text.toString().lowercase()
        }
        fragmentIdealWeightBinding.submitButton.setOnClickListener {
            fragmentIdealWeightBinding.loadingProgressBar.visibility = View.VISIBLE
            if (fragmentIdealWeightBinding.genderRadioGroup.checkedRadioButtonId == -1) {
                CommonMethod.loadPopUp("Select Gender", requireContext())
                fragmentIdealWeightBinding.loadingProgressBar.visibility = View.GONE
            } else if (fragmentIdealWeightBinding.heightEditText.text.toString().toInt() in 130..230) {
                weatherViewModel.getIdealWeightDetails(
                    genderString,
                    fragmentIdealWeightBinding.heightEditText.text.toString()
                )
            } else {
                CommonMethod.loadPopUp("Enter Height Between 130cm and 230cm", requireContext())
                fragmentIdealWeightBinding.loadingProgressBar.visibility = View.GONE
            }
            /*else {

            }*/
        }
    }
}