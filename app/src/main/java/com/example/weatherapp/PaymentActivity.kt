package com.example.weatherapp

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject


class PaymentActivity :AppCompatActivity(),PaymentResultWithDataListener {
    private lateinit var paymentButton: Button
    private var actionBar: ActionBar? = null
    private lateinit var toolbar: Toolbar
    private lateinit var successLayout: RelativeLayout
    private lateinit var transactionIdTextview: TextView
    private lateinit var contactTextview: TextView
    private lateinit var amountTextview: TextView
    private lateinit var continueShopping: TextView
    private lateinit var moveToCartTextView: TextView
    var totalAmount=""
    var value = 0
    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private val TAG = PaymentActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        paymentButton = findViewById(R.id.makePaymentButton)
        successLayout = findViewById(R.id.successLayout)
        transactionIdTextview = findViewById(R.id.transactionIdTextview)
        contactTextview = findViewById(R.id.contactTextview)
        continueShopping = findViewById(R.id.continueShopping)
        moveToCartTextView = findViewById(R.id.moveToCartTextView)
        amountTextview = findViewById(R.id.amountTextview)
        toolbar = findViewById(R.id.toolbar)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        toolbar.setTitle(null)
        actionBar = supportActionBar
        if (actionBar == null) {
            setSupportActionBar(toolbar)
            actionBar = supportActionBar
            actionBar?.elevation = 4.0f
            actionBar?.setDisplayHomeAsUpEnabled(false)
        } else {
            toolbar.visibility = View.GONE
        }
        Checkout.preload(applicationContext)
        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFF"))
        actionBar!!.setBackgroundDrawable(colorDrawable)
        actionBar!!.title = Html.fromHtml("<font color=${Color.parseColor("#6ec00c")}>Payment Status </font>")
        paymentButton.setOnClickListener {
            try {
                val sender = GMailSender("agritest7@gmail.com", "Test@123")
                sender.sendMail(
                    "Test",
                    "This mail is for testing purpose",
                    "agritest7@gmail.com",
                    "amal.nair@virenxia.com,agritest7@gmail.com"
                )
                Toast.makeText(this, "Mail Send", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                Log.e("Mail", e.message, e)
            }
        }
        continueShopping.setOnClickListener {

            finish()
        }

        moveToCartTextView.setOnClickListener {

        }



        startPayment()
    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val checkout = Checkout()
        val myIntent = intent // gets the previously created intent

        totalAmount = myIntent.getStringExtra("@TotalAmt").toString()
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
            checkout.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(payId: String?, data: PaymentData?) {
        successLayout.visibility=View.VISIBLE
        weatherViewModel.initCart()
        transactionIdTextview.text=payId
        amountTextview.text=totalAmount
        contactTextview.text=data?.userContact
       /* val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("@Cart",true)
        startActivity(intent)
        finish()*/
        Log.d(TAG,"Payment ID = $payId")
    }

    override fun onPaymentError(p0: Int, payId: String?, data: PaymentData?) {
        successLayout.visibility=View.GONE
        Log.d(TAG,"Error = $payId")
    }

/*    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            true
        } else super.onKeyDown(keyCode, event)
    }*/
}