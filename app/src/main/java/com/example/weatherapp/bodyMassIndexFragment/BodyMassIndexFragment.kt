package com.example.weatherapp.bodyMassIndexFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentBodyMassIndexBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class BodyMassIndexFragment : Fragment(R.layout.fragment_body_mass_index) {
    private val fragmentBodyMassIndexBinding by viewBinding(FragmentBodyMassIndexBinding::bind)
    private val TAG = BodyMassIndexFragment::class.java.simpleName

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
        weatherViewModel.bodyMassIndexResultLiveDataList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                fragmentBodyMassIndexBinding.loadingProgressBar.visibility = View.GONE
                fragmentBodyMassIndexBinding.bmiDetailsLayout.visibility = View.VISIBLE
                fragmentBodyMassIndexBinding.bmiTextview.text = it.results.bmi
                fragmentBodyMassIndexBinding.yourWeightTextview.text = it.results.health
                fragmentBodyMassIndexBinding.bmiRangeTextview.text =
                    "The Average BMI should be between ${it.results.healthy_bmi_range}"
            } else {
                fragmentBodyMassIndexBinding.bmiDetailsLayout.visibility = View.GONE
                CommonMethod.loadPopUp("Failed to retrieve data", requireContext())
                fragmentBodyMassIndexBinding.loadingProgressBar.visibility = View.GONE
            }

        })
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentBodyMassIndexBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentBodyMassIndexBinding.root, requireActivity())
            false
        }
        fragmentBodyMassIndexBinding.submitButton.setOnClickListener {
            fragmentBodyMassIndexBinding.loadingProgressBar.visibility = View.VISIBLE
            if (CommonMethod.isNetworkConnected(requireContext())) {
                if (!fragmentBodyMassIndexBinding.heightEditText.text.toString().isNullOrEmpty() &&
                    !fragmentBodyMassIndexBinding.weightEditText.text.toString().isNullOrEmpty() &&
                    !fragmentBodyMassIndexBinding.ageEditText.text.toString().isNullOrEmpty()) {
                    if (fragmentBodyMassIndexBinding.ageEditText.text.toString().toInt() in 1..80) {
                        weatherViewModel.getBMIDetails(
                            fragmentBodyMassIndexBinding.ageEditText.text.toString(),
                            fragmentBodyMassIndexBinding.weightEditText.text.toString(),
                            fragmentBodyMassIndexBinding.heightEditText.text.toString())
                    } else {
                        fragmentBodyMassIndexBinding.bmiDetailsLayout.visibility = View.GONE
                        CommonMethod.loadPopUp(
                            "Please Enter a Valid Age Between 1 and 80",
                            requireContext()
                        )
                        fragmentBodyMassIndexBinding.loadingProgressBar.visibility = View.GONE
                    }

                } else {
                    fragmentBodyMassIndexBinding.bmiDetailsLayout.visibility = View.GONE
                    CommonMethod.loadPopUp("Values should not be empty", requireContext())
                    // CommonMethod.loadPopUp("Please Enter a Valid Age Between 1 and 80",requireContext())
                    fragmentBodyMassIndexBinding.loadingProgressBar.visibility = View.GONE
                }

            } else {
                Navigation.findNavController(fragmentBodyMassIndexBinding.root)
                    .navigate(R.id.action_bmi_to_health_cate)
            }
        }
    }
}