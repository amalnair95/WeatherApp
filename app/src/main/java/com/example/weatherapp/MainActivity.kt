package com.example.weatherapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var actionBar: ActionBar? = null
    private var toolbarMenu: Menu? = null
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private var cartQuantity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonMethod.loadLocaleLang(this)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(null)
        actionBar = supportActionBar
        if (actionBar == null) {
            setSupportActionBar(toolbar)
            actionBar = supportActionBar
            actionBar?.elevation = 4.0f
            actionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            toolbar.visibility = View.GONE
        }
        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFF"))
        // Set BackgroundDrawable
        actionBar!!.setBackgroundDrawable(colorDrawable)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val extras = intent.extras
        if (extras != null && (extras.containsKey("ChooseProduct") || extras.containsKey("@Cart"))){
            val chooseProduct: Boolean = extras.getBoolean("ChooseProduct")
            val cart: Boolean = extras.getBoolean("@Cart")
            if (chooseProduct) {
                navController?.navigate(R.id.productCategoryFragment)
            }else if(cart){
                navController?.navigate(R.id.cartFragment)
            }
        }
        bottomNavigationView.setupWithNavController(navController!!)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        navController!!.addOnDestinationChangedListener { controller, destination, arguments ->

            //actionBar!!.title = destination.label
            actionBar!!.title =
                Html.fromHtml("<font color=${Color.parseColor("#6ec00c")}>" + destination.label + "</font>")
            when (destination.id) {
                R.id.categoryFragment -> showBottomNav()
                R.id.miscCategoryFragment -> showBottomNav()
                R.id.healthCategoryFragment -> showBottomNav()
                R.id.productCategoryFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }


     setObservers()
    }

    fun setObservers(){
        weatherViewModel.getCart().observe(this, Observer {
            var quantity = 0
            for (cartItem in it) {
                quantity += cartItem.quantity
            }
            cartQuantity = quantity
            invalidateOptionsMenu()
        })
    }

    private fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        CommonMethod.loadLocaleLang(this)
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val userProfileItem = menu.findItem(R.id.userProfile)
        userProfileItem.isVisible = false
        val scannerItem = menu.findItem(R.id.scanner)
        scannerItem.isVisible = false
        val logoutProfileItem = menu.findItem(R.id.logoutProfile)
        logoutProfileItem.isVisible = false
        val shoppingCartItem = menu.findItem(R.id.shoppingCart)
        shoppingCartItem.isVisible = false
        val actionView = shoppingCartItem.actionView
        val cartBadgeTextView = actionView.findViewById<TextView>(R.id.cart_badge_text_view)
        if (cartQuantity==0){
            cartBadgeTextView.visibility=View.GONE
        }else{
            cartBadgeTextView.visibility=View.VISIBLE
        }
        cartBadgeTextView.text = cartQuantity.toString()
        actionView.setOnClickListener {
            onOptionsItemSelected(shoppingCartItem)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.appTranslate -> {
                CommonMethod.showLanguageChange(this, this)
            }
            R.id.shoppingCart -> {
                navController?.navigate(R.id.cartFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miscFrag -> {
                Log.d("TAG", "inside misc category")
                navController?.navigate(R.id.miscCategoryFragment)
            }
            R.id.homeFrag -> {
                Log.d("TAG", "inside home")
                navController?.navigate(R.id.categoryFragment)
            }
            R.id.healthFrag -> {
                Log.d("TAG", "inside Health")
                navController?.navigate(R.id.healthCategoryFragment)
            }
            R.id.productFrag -> {
                Log.d("TAG", "inside Shop")
                navController?.navigate(R.id.productCategoryFragment)
            }
            else -> {
                Log.d("TAG", "No Option selected")
            }
        }
        return true
    }

 /*   override fun onPaymentSuccess(payId: String?, paymentData: PaymentData?) {
        Log.d("TAG","Payment Success")
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
        navController?.navigate(R.id.paymentFragment)
    }

    override fun onPaymentError(p0: Int, payId: String?, paymentData: PaymentData?) {
        Log.d("TAG","Payment Failure")
        Toast.makeText(this, "Payment Failure", Toast.LENGTH_SHORT).show()
    }*/

}