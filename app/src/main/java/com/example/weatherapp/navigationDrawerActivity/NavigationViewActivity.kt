package com.example.weatherapp.navigationDrawerActivity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import android.widget.ExpandableListView
import androidx.navigation.NavController
import androidx.appcompat.app.ActionBarDrawerToggle
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.weatherapp.R
import com.google.android.material.navigation.NavigationView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.weatherapp.weatherFragment.WeatherFragment
import com.example.weatherapp.airFragment.AirFragment
import com.example.weatherapp.soilFragment.SoilFragment
import com.example.weatherapp.pollenFragment.PollenFragment
import com.example.weatherapp.fireFragment.FireFragment
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import java.util.ArrayList
import java.util.HashMap

class NavigationViewActivity : AppCompatActivity() {
    private var mDrawerLayout: DrawerLayout? = null
    var mMenuAdapter: ExpandableListAdapter? = null
    var expandableList: ExpandableListView? = null
    var listDataHeader: MutableList<ExpandedMenuModel>? = mutableListOf()
    private val navController: NavController? = null
    private val actionBar: ActionBar? = null
    var toolbar: Toolbar? = null
    var mDrawerToggle: ActionBarDrawerToggle? = null
    var selectedItem: String? = null
    var logoImageView:ImageView? = null
    var listDataChild: HashMap<ExpandedMenuModel, List<String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_view)
        val ab = supportActionBar
        /* to set the menu icon image*/
        ab!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        ab.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        expandableList = findViewById<View>(R.id.navigationmenu) as ExpandableListView
        logoImageView = findViewById<View>(R.id.logoImageView) as ImageView
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView?.let { setupDrawerContent(it) }
        permissionSetup()
        //setupToolbar();

        //setupDrawerToggle();
        val listHeaderView = layoutInflater.inflate(R.layout.nav_header, null, false)
        expandableList!!.addHeaderView(listHeaderView)
        prepareListData()
        mMenuAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList)

        // setting list adapter
        expandableList!!.setAdapter(mMenuAdapter)
        expandableList!!.setOnChildClickListener { expandableListView, view, i, i1, l ->
            Log.d("DEBUG", "submenu item clicked $i$i1")
            logoImageView?.visibility=View.GONE
            selectedItem = listDataChild!![listDataHeader!![i]]!![i1]
            println("Selected Item $selectedItem")
            supportActionBar!!.title = selectedItem
            when (selectedItem) {
                "Weather Details" -> {
                    val weatherFragment: Fragment = WeatherFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, weatherFragment).commit()
                    //showBackButton(true);
                    mDrawerLayout!!.closeDrawers()
                }
                "Air Details" -> {
                    val airFragment: Fragment = AirFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, airFragment)
                        .commit()
                    //showBackButton(true);
                    mDrawerLayout!!.closeDrawers()
                }
                "Soil Details" -> {
                    val soilFragment: Fragment = SoilFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, soilFragment)
                        .commit()
                    //showBackButton(true);
                    mDrawerLayout!!.closeDrawers()
                }
                "Pollen Details" -> {
                    val pollenFragment: Fragment = PollenFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, pollenFragment).commit()
                    //showBackButton(true);
                    mDrawerLayout!!.closeDrawers()
                }
                "Fire Details" -> {
                    val fireFragment: Fragment = FireFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fireFragment)
                        .commit()
                    //showBackButton(true);
                    mDrawerLayout!!.closeDrawers()
                }
            }
            false
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbarNavigation)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun setupDrawerToggle() {
        mDrawerToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle!!.toolbarNavigationClickListener = View.OnClickListener {
            if (mDrawerToggle!!.isDrawerIndicatorEnabled) {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                Log.d("test","drawer opened")
            } else {
                onBackPressed()
            }
        }
        mDrawerLayout!!.addDrawerListener(mDrawerToggle!!)
        mDrawerToggle!!.syncState()
    }

    private fun prepareListData() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()
        val item1 = ExpandedMenuModel()
        item1.setIconName("Weather")
        item1.setIconImg(R.drawable.raining)
        // Adding data header
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item1)
        val item2 = ExpandedMenuModel()
        item2.setIconName("Air Quality")
        item2.setIconImg(R.drawable.clean_air)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item2)
        val item3 = ExpandedMenuModel()
        item3.setIconName("Soil")
        item3.setIconImg(R.drawable.soil)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item3)
        val item4 = ExpandedMenuModel()
        item4.setIconName("Pollen")
        item4.setIconImg(R.drawable.pollen)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item4)
        val item5 = ExpandedMenuModel()
        item5.setIconName("Fire")
        item5.setIconImg(R.drawable.fire)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item5)
        // Adding child data
        val heading1: MutableList<String> = ArrayList()
        heading1.add("Weather Details")
        val heading2: MutableList<String> = ArrayList()
        heading2.add("Air Details")
        val heading3: MutableList<String> = ArrayList()
        heading3.add("Soil Details")
        val heading4: MutableList<String> = ArrayList()
        heading4.add("Pollen Details")
        val heading5: MutableList<String> = ArrayList()
        heading5.add("Fire Details")
        listDataChild!![(listDataHeader as ArrayList<ExpandedMenuModel>)[0]] = heading1 // Header, Child data
        listDataChild!![(listDataHeader as ArrayList<ExpandedMenuModel>)[1]] = heading2
        listDataChild!![(listDataHeader as ArrayList<ExpandedMenuModel>)[2]] = heading3
        listDataChild!![(listDataHeader as ArrayList<ExpandedMenuModel>)[3]] = heading4
        listDataChild!![(listDataHeader as ArrayList<ExpandedMenuModel>)[4]] = heading5
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            mDrawerLayout!!.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mDrawerLayout!!.closeDrawers()
            true
        }
    }

    fun showBackButton(isBack: Boolean) {
        mDrawerToggle!!.isDrawerIndicatorEnabled = !isBack
        supportActionBar!!.setDisplayHomeAsUpEnabled(isBack)
        mDrawerToggle!!.syncState()
    }

    override fun onBackPressed() {
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            val fm = supportFragmentManager
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            } else {
                super.onBackPressed()
            }
            showBackButton(false)
            supportActionBar!!.title = title
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun permissionSetup() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
            ))
        } else {
            println("Permission isGranted")
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){ permissions->
        val granted = permissions.entries.all {
            it.value == true
        }
        if (granted){
            println("Permission has been granted by user")
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            //requireActivity().finish()
        }
    }
}