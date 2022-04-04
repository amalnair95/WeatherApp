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
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databaseFiles.DatabaseHelper
import com.example.weatherapp.databinding.FragmentCategoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseCategoryFragment : Fragment(R.layout.fragment_category) {

    private val fragmentCategoryBinding by viewBinding(FragmentCategoryBinding::bind)
    private val TAG = ChooseCategoryFragment::class.java.simpleName
    var userReceived: String=""
    var percentCompleted: String=""
    private var dbHelper: DatabaseHelper?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        permissionSetup()
        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.userProfile ->{
                Log.d(TAG,"username:$userReceived")
                val args = Bundle()
                args.putString("@USERNAME", userReceived)
                args.putString("@PROGRESSBAR", percentCompleted)
                Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_user_profile,args)
            }
            R.id.scanner->{
                Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_scanner)
            }
            R.id.logoutProfile->{
                val checkBiQuery= dbHelper!!.ExecuteBiQuery("UPDATE TokenDetails SET RefreshToken=NULL where UserName='$userReceived'")
                Navigation.findNavController(fragmentCategoryBinding.root).navigate(R.id.action_category_to_selection)
            }
        }
        return super.onOptionsItemSelected(item)
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
        dbHelper= DatabaseHelper(requireContext())
        val bundle = arguments
        if (bundle != null) {
            userReceived = bundle.getString("@USERNAME").toString()
            percentCompleted = bundle.getString("@PROGRESSBAR").toString()
            Log.d(TAG,"UserName:$userReceived")
        }


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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}