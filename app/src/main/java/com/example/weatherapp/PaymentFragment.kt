package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentPaymentBinding
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.razorpay.Checkout

import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.json.JSONObject

class PaymentFragment :Fragment(R.layout.fragment_payment) {
    private val fragmentPaymentBinding by viewBinding(FragmentPaymentBinding::bind)
    var totalAmount=""
    var value = 0
    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private val TAG = PaymentFragment::class.java.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {

    }
    private fun init() {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        Checkout.preload(activity?.applicationContext)
        startPayment()
        fragmentPaymentBinding.makePaymentButton.setOnClickListener {
            try {
                val sender = GMailSender("agritest7@gmail.com", "Test@123")
                sender.sendMail(
                    "Test",
                    "This mail is for testing purpose",
                    "agritest7@gmail.com",
                    "amal.nair@virenxia.com,agritest7@gmail.com"
                )
                Toast.makeText(requireContext(), "Mail Send", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                Log.e("Mail", e.message, e)
            }
        }
    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val checkout = Checkout()
        val myIntent = activity?.intent // gets the previously created intent
        val bundle = arguments
        if (bundle != null) {
            totalAmount = bundle.getString("@TotalAmt").toString()
            Log.d(TAG,"Total Amount:$totalAmount")
        }
        //totalAmount = myIntent?.getStringExtra("@TotalAmt").toString()
        if (totalAmount.isNotEmpty()){
            value= totalAmount.toDouble().toInt()
            value *= 100
        }

        checkout.setKeyID("rzp_test_d6EV1BLZ3GNmEN")
        checkout.setImage(R.drawable.logo)
        try {
            val options = JSONObject()
            options.put("name","VirenXia Limited")
            options.put("description","Total Product Charges")
            //You can omit the image option to fetch the image from dashboard
            // options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            // options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#fffff");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            //this amount is actually 500 because razorpay divides the amount in object with 100
            //so if the user enters 500 obj takes it as 50000
            //options.put("amount","50000")//pass amount in currency subunits
            options.put("amount",value.toString())//pass amount in currency subunits
            options.put("prefill.email","gaurav.kumar@example.com")
            options.put("prefill.contact","8652476302")

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            /*val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","8652476302")

            options.put("prefill",prefill)*/
            checkout.open(requireActivity(),options)
            //fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss();
        }catch (e: Exception){
            Toast.makeText(requireContext(),"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}