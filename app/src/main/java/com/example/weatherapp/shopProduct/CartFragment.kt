package com.example.weatherapp.shopProduct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.PaymentActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCartBinding
import com.example.weatherapp.models.CartItem
import com.example.weatherapp.weatherFragment.WeatherViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class CartFragment : Fragment(R.layout.fragment_cart), CartListAdapter.OnRemoveProductListener {
    private val fragmentCartBinding by viewBinding(FragmentCartBinding::bind)
    private val TAG = CartFragment::class.java.simpleName

    //viewModel
    private lateinit var weatherViewModel: WeatherViewModel

    // adapter
    private lateinit var cartListAdapter: CartListAdapter

    private lateinit var totalAmount: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
        setObservers()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

        fragmentCartBinding.placeOrderButton.setOnClickListener {
            val intent = Intent(activity, PaymentActivity::class.java)
            intent.putExtra("@TotalAmt",totalAmount)
            startActivity(intent)
           /* val args = Bundle()
            args.putString("@TotalAmt", totalAmount)
            Navigation.findNavController(fragmentCartBinding.root).navigate(R.id.action_cart_to_payment,args)*/
        }



    }

    private fun setObservers() {
        weatherViewModel.getCart().observe(viewLifecycleOwner, object : Observer<List<CartItem>> {
            override fun onChanged(t: List<CartItem>?) {
                Log.d(TAG, "onChanged ${t?.size}")
                if (t?.size!! > 0) {
                    cartListAdapter = CartListAdapter(requireContext(), t, this@CartFragment)
                    fragmentCartBinding.cartRecyclerView.visibility = View.VISIBLE
                    fragmentCartBinding.buttonLayout.visibility = View.VISIBLE
                    fragmentCartBinding.noProductImageView.visibility = View.GONE
                    fragmentCartBinding.noProductTextView.visibility = View.GONE
                    fragmentCartBinding.cartRecyclerView.adapter = cartListAdapter
                    fragmentCartBinding.cartRecyclerView.itemAnimator = DefaultItemAnimator()
                    fragmentCartBinding.cartRecyclerView.layoutManager = LinearLayoutManager(context)

                } else {
                    fragmentCartBinding.cartRecyclerView.visibility = View.GONE
                    fragmentCartBinding.buttonLayout.visibility = View.GONE
                    fragmentCartBinding.noProductImageView.visibility = View.VISIBLE
                    fragmentCartBinding.noProductTextView.visibility = View.VISIBLE
                }
            }
        })

        weatherViewModel.getTotalPrice().observe(viewLifecycleOwner, Observer {
            totalAmount=it.toString()
            fragmentCartBinding.orderTotalTextView.text = "Total: ₹ $totalAmount "
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.appTranslate).isVisible = false
    }

    override fun removeItem(cartItem: CartItem) {
        Log.d(TAG, "item to be removed: ${cartItem.products.productName}")
        weatherViewModel.removeItem(cartItem)
    }

    override fun changeQuantity(cartItem: CartItem, quantity: Int) {
        weatherViewModel.changeQuantity(cartItem, quantity)
    }
}