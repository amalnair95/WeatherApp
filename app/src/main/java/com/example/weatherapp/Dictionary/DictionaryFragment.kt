package com.example.weatherapp.Dictionary

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentDictionaryBinding
import com.example.weatherapp.models.WordDetails
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {
    private val fragmentDictionaryBinding by viewBinding(FragmentDictionaryBinding::bind)
    private val TAG = DictionaryFragment::class.java.simpleName

    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel

    // collections
    private val wordDetailsList: MutableList<WordDetails> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        weatherViewModel.wordDetailResultLiveDataList.observe(viewLifecycleOwner, Observer {
            if (!it.definition.isNullOrEmpty()){
                fragmentDictionaryBinding.loadingProgressBar.visibility = View.GONE
                fragmentDictionaryBinding.wordDetailsLayout.visibility=View.VISIBLE
                fragmentDictionaryBinding.wordTextview.text = it.word
                fragmentDictionaryBinding.meaningTextview.text = it.definition
            }else{
                fragmentDictionaryBinding.wordDetailsLayout.visibility=View.GONE
                CommonMethod.loadPopUp("Please Enter a Valid Word",requireContext())
                fragmentDictionaryBinding.loadingProgressBar.visibility = View.GONE
            }

        })
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentDictionaryBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentDictionaryBinding.root, requireActivity())
            false
        }

        fragmentDictionaryBinding.dictWordEditText.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= fragmentDictionaryBinding.dictWordEditText.right - fragmentDictionaryBinding.dictWordEditText.compoundDrawables.get(DRAWABLE_RIGHT).bounds.width()) {
                    Log.d(TAG, "Entered Text: ${fragmentDictionaryBinding.dictWordEditText.text.toString()}")
                    fragmentDictionaryBinding.loadingProgressBar.visibility = View.VISIBLE
                    if (CommonMethod.isNetworkConnected(requireContext())) {
                        weatherViewModel.getWordDetails(fragmentDictionaryBinding.dictWordEditText.text.toString())
                    }else{
                        Navigation.findNavController(fragmentDictionaryBinding.root).navigate(R.id.action_food_recipe_to_category)
                    }
                    return@OnTouchListener true
                }
            }
            false
        })

        fragmentDictionaryBinding.submitButton.setOnClickListener {
            fragmentDictionaryBinding.loadingProgressBar.visibility = View.VISIBLE
            if (CommonMethod.isNetworkConnected(requireContext())) {
                weatherViewModel.getWordDetails(fragmentDictionaryBinding.dictWordEditText.text.toString())
            }else{
                Navigation.findNavController(fragmentDictionaryBinding.root).navigate(R.id.action_dictionary_to_misc_cate)
            }

        }
    }
}