package com.example.weatherapp.foodFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.animation.AnimationUtils
import com.example.weatherapp.databinding.ItemProductBinding
import com.example.weatherapp.models.FoodDetails

class FoodDetailsAdapter(val context: Context?, private val foodDetailsList: List<FoodDetails>) :
    RecyclerView.Adapter<FoodDetailsAdapter.ProductViewHolder>() {
    private val TAG: String = FoodDetailsAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemProductBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemProductBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val foodDetail = foodDetailsList[position]
        holder.itemProductBinding.itemNameTextView.text = foodDetail.name.toString().uppercase()
        holder.itemProductBinding.itemNameTextView.setOnClickListener {
            if (!holder.itemProductBinding.foodDetailsLayout.isVisible) {
                //AnimationUtils.rotateView(true,holder.itemProductBinding.foodDetailsLayout)
                //AnimationUtils.toggleExpand(true,holder.itemProductBinding.foodDetailsLayout)
                    AnimationUtils.expand(holder.itemProductBinding.foodDetailsLayout)
                //holder.itemProductBinding.foodDetailsLayout.visibility = View.VISIBLE
                holder.itemProductBinding.itemNameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0)
                holder.itemProductBinding.caloriesTextview.text = "${foodDetail.calories} cal"
                holder.itemProductBinding.fatTextview.text = "${foodDetail.fat.toString()} gm"
                holder.itemProductBinding.saturatedFatTextview.text = "${foodDetail.saturatedFat.toString()} gm"
                holder.itemProductBinding.proteinTextview.text = "${foodDetail.protein.toString()} gm"
                holder.itemProductBinding.sodiumTextview.text = "${foodDetail.sodium.toString()} mg"
                holder.itemProductBinding.potassiumTextview.text = "${foodDetail.potassium.toString()} mg"
                holder.itemProductBinding.cholesterolTextview.text = "${foodDetail.cholesterol.toString()} mg"
                holder.itemProductBinding.carbohydratesTextview.text = "${foodDetail.carbohydrates.toString()} gm"
                holder.itemProductBinding.fiberTextview.text = "${foodDetail.fiber.toString()} gm"
                holder.itemProductBinding.sugarTextview.text = "${foodDetail.sugar.toString()} gm"
            } else {
                //AnimationUtils.toggleExpand(false,holder.itemProductBinding.foodDetailsLayout)
                AnimationUtils.collapse(holder.itemProductBinding.foodDetailsLayout)
                //holder.itemProductBinding.foodDetailsLayout.visibility = View.GONE
                holder.itemProductBinding.itemNameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0)
            }

        }
    }

    override fun getItemCount(): Int {
        return foodDetailsList.size
    }

    class ProductViewHolder(val itemProductBinding: ItemProductBinding) : RecyclerView.ViewHolder(itemProductBinding.root)

}