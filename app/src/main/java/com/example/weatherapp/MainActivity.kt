package com.example.weatherapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigatin_view)

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
        val colorDrawable = ColorDrawable(Color.parseColor("#D9EDBF"))
        // Set BackgroundDrawable
        actionBar!!.setBackgroundDrawable(colorDrawable)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController!!)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        navController!!.addOnDestinationChangedListener { controller, destination, arguments ->
            actionBar!!.title = destination.label
            when (destination.id) {
                R.id.categoryFragment -> showBottomNav()
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.profileFrag) {
            Log.d("TAG", "inside Profile")
            navController?.navigate(R.id.userProfileFragment)
        }else {
            Log.d("TAG", "No Option selected")
        }
        return true
    }

}