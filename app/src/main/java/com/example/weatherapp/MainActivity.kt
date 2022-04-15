package com.example.weatherapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.commonMethod.CommonMethod
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var actionBar: ActionBar? = null
    private var toolbarMenu: Menu? = null
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonMethod.loadLocaleLang(this)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

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
        bottomNavigationView.setupWithNavController(navController!!)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        navController!!.addOnDestinationChangedListener { controller, destination, arguments ->

            //actionBar!!.title = destination.label
            actionBar!!.title = Html.fromHtml("<font color=${Color.parseColor("#6ec00c")}>" + destination.label + "</font>")
            when (destination.id) {
                R.id.categoryFragment -> showBottomNav()
                R.id.miscCategoryFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }

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
        inflater.inflate(R.menu.main,menu)
        val userProfileItem = menu.findItem(R.id.userProfile)
        userProfileItem.isVisible = false
        val scannerItem = menu.findItem(R.id.scanner)
        scannerItem.isVisible = false
        val logoutProfileItem = menu.findItem(R.id.logoutProfile)
        logoutProfileItem.isVisible = false
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.appTranslate->{
                CommonMethod.showLanguageChange(this,this)
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
            else -> {
                Log.d("TAG", "No Option selected")
            }
        }
        return true
    }

}