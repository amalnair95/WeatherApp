package com.example.weatherapp.chooseProduct

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.ItemAgriProductsBinding
import com.example.weatherapp.models.Products

class ProductDetailsAdapter(val context: Context?, private val productList: List<Products>,val onAddProductSelectedListener: OnAddProductSelectedListener) :
    RecyclerView.Adapter<ProductDetailsAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemAgriProductsBinding =
            ItemAgriProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemAgriProductsBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productDetail = productList[position]
        holder.itemAgriProductsBinding.productNameQuantityTextView.text =
            "Farmics ${productDetail.productName} ${productDetail.quantity} by Virenxia"

        if (productDetail.discount != 0) {
            holder.itemAgriProductsBinding.discountTextView.text =
                "-${productDetail.discount.toString()}% Off"
            holder.itemAgriProductsBinding.priceTextView.paintFlags =
                holder.itemAgriProductsBinding.priceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            val a: Int = productDetail.price
            val b: Float = productDetail.discount.toFloat() / 100
            holder.itemAgriProductsBinding.priceTextView.text = "MRP: ₹ " + productDetail.price
            val c = (a * b).toInt()
            val d = a - c
            holder.itemAgriProductsBinding.discountedPriceTextView.text = "MRP: ₹ $d"
            holder.itemAgriProductsBinding.discountedPriceTextView.visibility = View.VISIBLE
            holder.itemAgriProductsBinding.discountTextView.visibility = View.VISIBLE
        } else {
            holder.itemAgriProductsBinding.priceTextView.text = "MRP: \u20B9${productDetail.price}"
            holder.itemAgriProductsBinding.discountedPriceTextView.visibility = View.GONE
            holder.itemAgriProductsBinding.discountTextView.visibility = View.GONE
        }
        CommonMethod.loadImage(
            context!!,
            "product_${productDetail.productId}",
            holder.itemAgriProductsBinding.productImageView
        )
        holder.itemAgriProductsBinding.addItemButton.setOnClickListener {
            onAddProductSelectedListener.addItem(productDetail)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(val itemAgriProductsBinding: ItemAgriProductsBinding) :
        RecyclerView.ViewHolder(itemAgriProductsBinding.root)

    interface OnAddProductSelectedListener {
        fun addItem(product: Products)
    }
}