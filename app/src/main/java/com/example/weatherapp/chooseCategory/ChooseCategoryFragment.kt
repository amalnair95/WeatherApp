package com.example.weatherapp.chooseCategory

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCategoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseCategoryFragment : Fragment(R.layout.fragment_category) {

    private val fragmentCategoryBinding by viewBinding(FragmentCategoryBinding::bind)
    private val TAG = ChooseCategoryFragment::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        view.isFocusableInTouchMode=true
        view.requestFocus()
        view.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action === KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true
                    }
                }
                return false
            }
        })


        permissionSetup()
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {

    }

    private fun permissionSetup() {
        val permission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(arrayOf(ACCESS_COARSE_LOCATION,CAMERA))
        } else {
            println("Permission isGranted")
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){permissions->
        val granted = permissions.entries.all {
            it.value == true
        }
        if (granted){
            println("Permission has been granted by user")
        }else{
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            //requireActivity().finish()
        }
    }

    private fun init() {
        fragmentCategoryBinding.weatherCardView.setOnClickListener {
            Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_weather)
           /* Navigation.findNavController(fragmentCategoryBinding.root)
                .navigate(R.id.action_category_to_register)*/
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