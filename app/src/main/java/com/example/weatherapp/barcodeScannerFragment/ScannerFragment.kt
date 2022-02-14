package com.example.weatherapp.barcodeScannerFragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentScannerBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class ScannerFragment : Fragment(R.layout.fragment_scanner) {
    private val fragmentScannerBinding by viewBinding(FragmentScannerBinding::bind)
    private lateinit var codeScanner: CodeScanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkCameraPermission()
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (requireContext() as Activity),
                    arrayOf(Manifest.permission.CAMERA),
                    1
                )
            }
        }
    }

    /* @JvmName("onRequestPermissionsResult1")
     fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>?, grantResults: IntArray) {
         when (requestCode) {
             1 ->
                 // If request is cancelled, the result arrays are empty.
                 if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     init()
                 } else {
                     Toast.makeText(activity, "You declined to allow the app to access your camera",
                         Toast.LENGTH_LONG).show()
                 }
         }
     }
 */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //init()
            }
        }
    }

    private fun init() {
        codeScanner = CodeScanner(requireContext(), fragmentScannerBinding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false
        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                Toast.makeText(activity, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                print("Scan result:${it.text}")
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity?.runOnUiThread {
                Toast.makeText(
                    activity,
                    "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
                print("Camera initialization error:${it.message}")
            }
        }

        fragmentScannerBinding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    override fun onResume() {
        super.onResume()
//        codeScanner = CodeScanner(requireContext(), fragmentScannerBinding.scannerView)
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}