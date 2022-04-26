package com.example.weatherapp.shopProduct

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.chooseProduct.ProductDetailsAdapter
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.ItemShoppingCartBinding
import com.example.weatherapp.models.CartItem
import com.example.weatherapp.models.Products


class CartListAdapter(val context: Context?, private val cartList: List<CartItem>,val onRemoveProductListener:OnRemoveProductListener) : RecyclerView.Adapter<CartListAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemShoppingCartBinding = ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemShoppingCartBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartDetail = cartList[position]
        //val quantity = holder.itemShoppingCartBinding.quantitySpinner.selectedItem.toString()
        holder.itemShoppingCartBinding.quantitySpinner.setSelection(getIndex(holder.itemShoppingCartBinding.quantitySpinner, cartDetail.quantity.toString()))
        val quantity = holder.itemShoppingCartBinding.quantitySpinner.selectedItem.toString()
        cartDetail.quantity=quantity.toInt()
        holder.itemShoppingCartBinding.productNameQuantityTextView.text =
            "Farmics ${cartDetail.products.productName} ${cartDetail.products.quantity} by Virenxia"
        CommonMethod.loadImage(context!!, "product_${cartDetail.products.productId}", holder.itemShoppingCartBinding.productImageView)
        if (cartDetail.products.discount != 0) {
            holder.itemShoppingCartBinding.discountTextView.text =
                "-${cartDetail.products.discount.toString()}% Off"
            holder.itemShoppingCartBinding.priceTextView.paintFlags =
                holder.itemShoppingCartBinding.priceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            val a: Int = cartDetail.products.price * cartDetail.quantity
            val b: Float = cartDetail.products.discount.toFloat() / 100
            holder.itemShoppingCartBinding.priceTextView.text = "MRP: ₹ $a"
            val c = (a * b).toInt()
            val d = a - c
            holder.itemShoppingCartBinding.discountedPriceTextView.text = "MRP: ₹ $d"
            holder.itemShoppingCartBinding.discountedPriceTextView.visibility = View.VISIBLE
            holder.itemShoppingCartBinding.discountTextView.visibility = View.VISIBLE
        }else{
            val price=cartDetail.products.price * cartDetail.quantity
            holder.itemShoppingCartBinding.priceTextView.text = "MRP: \u20B9$price"
            holder.itemShoppingCartBinding.discountedPriceTextView.visibility = View.GONE
            holder.itemShoppingCartBinding.discountTextView.visibility = View.GONE
        }
        holder.itemShoppingCartBinding.removeItemButton.setOnClickListener {
            onRemoveProductListener.removeItem(cartDetail)
        }
        holder.itemShoppingCartBinding.quantitySpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val newQuantity = position + 1
                if(newQuantity == cartDetail.quantity){
                    return
                }
                onRemoveProductListener.changeQuantity(cartDetail,newQuantity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return -1
    }

    class CartViewHolder(val itemShoppingCartBinding: ItemShoppingCartBinding) : RecyclerView.ViewHolder(itemShoppingCartBinding.root)

    interface OnRemoveProductListener {
        fun removeItem(cartItem: CartItem)
        fun changeQuantity(cartItem: CartItem,quantity:Int)
    }
}