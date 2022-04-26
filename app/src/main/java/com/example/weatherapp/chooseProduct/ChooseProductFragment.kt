package com.example.weatherapp.chooseProduct

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.ChooseProductFragmentBinding
import com.example.weatherapp.models.CartItem
import com.example.weatherapp.models.Products
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseProductFragment : Fragment(R.layout.choose_product_fragment),
    ProductDetailsAdapter.OnAddProductSelectedListener {
    private val fragmentChooseProductFragmentBinding by viewBinding(ChooseProductFragmentBinding::bind)
    private val TAG = ChooseProductFragment::class.java.simpleName

    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel

    // adapter
    private lateinit var productDetailsAdapter: ProductDetailsAdapter

    // collections
    private val productDetailsList: MutableList<Products> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        CommonMethod.backButtonCode(view)
        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.shoppingCart).isVisible = true
        menu.findItem(R.id.appTranslate).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
          R.id.shoppingCart->{
              Navigation.findNavController(fragmentChooseProductFragmentBinding.root).navigate(R.id.action_category_to_cart)
          }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        weatherViewModel.productListLiveDataList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Product list $it")
            if (it != null) {
                productDetailsList.clear()
                productDetailsList.addAll(it)
                productDetailsAdapter = ProductDetailsAdapter(context, productDetailsList, this)
                fragmentChooseProductFragmentBinding.itemRecyclerView.itemAnimator = DefaultItemAnimator()
                fragmentChooseProductFragmentBinding.itemRecyclerView.layoutManager = LinearLayoutManager(context)
                fragmentChooseProductFragmentBinding.itemRecyclerView.adapter = productDetailsAdapter
                fragmentChooseProductFragmentBinding.loadingProgressBar.visibility = View.GONE
            } else {
                CommonMethod.loadPopUp("No product found", requireContext())
                fragmentChooseProductFragmentBinding.loadingProgressBar.visibility = View.GONE
            }

        })

        weatherViewModel.getCart().observe(viewLifecycleOwner,object :Observer<List<CartItem>>{
            override fun onChanged(t: List<CartItem>?) {
            Log.d(TAG,"onChanged ${t?.size}")
            }
        })
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.clearResultSet()
        fragmentChooseProductFragmentBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentChooseProductFragmentBinding.root, requireActivity())
            false
        }

        fragmentChooseProductFragmentBinding.loadingProgressBar.visibility = View.VISIBLE
        weatherViewModel.getAllProducts(requireContext())

    }

    override fun addItem(product: Products) {
        Log.d(TAG, "Selected Product is ${product.productName}")
        val isAdded=weatherViewModel.addItemToCart(product)
        Log.d(TAG, " Product ${product.productName} is added $isAdded")
        if(!isAdded){
            Snackbar.make(requireView(),"Already have maximum quantity in cart",Snackbar.LENGTH_LONG).show()
        }
    }
}