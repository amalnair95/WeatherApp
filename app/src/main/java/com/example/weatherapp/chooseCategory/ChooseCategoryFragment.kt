package com.example.weatherapp.chooseCategory

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.budiyev.android.codescanner.CodeScanner
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCategoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseCategoryFragment : Fragment(R.layout.fragment_category) {

    private val fragmentCategoryBinding by viewBinding(FragmentCategoryBinding::bind)
    private val TAG = ChooseCategoryFragment::class.java.simpleName
    private lateinit var scannerMenuItem: MenuItem
    private lateinit var scanner: CodeScanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

/*    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        print("inside on create options menu")
        inflater.inflate(R.menu.main, menu)
        //scannerMenuItem = menu.findItem(R.id.scanner)
        //scanner=scannerMenuItem.actionView as CodeScanner
        //scanner.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.scanner -> {
                //CommonMethod.log(TAG, "Home button is pressed")
                print("Scanner button is pressed")
                Navigation.findNavController(fragmentCategoryBinding.root)
                    .navigate(R.id.action_category_to_scanner)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }*/

    private fun setObservers() {

    }

    private fun init() {
        fragmentCategoryBinding.weatherCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_weather)
        }
        fragmentCategoryBinding.airCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_air)
        }
        fragmentCategoryBinding.pollenCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_pollen)
        }
        fragmentCategoryBinding.soilCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_soil)
        }

        fragmentCategoryBinding.fireCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_fire)
        }
        fragmentCategoryBinding.scannerCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_scanner)
        }
    }


}