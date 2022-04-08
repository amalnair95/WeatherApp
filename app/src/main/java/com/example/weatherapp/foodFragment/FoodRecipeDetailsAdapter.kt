package com.example.weatherapp.foodFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.animation.AnimationUtils
import com.example.weatherapp.databinding.ItemRecipeProductItemBinding
import com.example.weatherapp.models.FoodRecipeDetails

class FoodRecipeDetailsAdapter(val context: Context?, private val foodRecipeDetailsList: List<FoodRecipeDetails>) : RecyclerView.Adapter<FoodRecipeDetailsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecipeDetailsAdapter.ProductViewHolder {
        val itemReceipeProductItemBinding = ItemRecipeProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemReceipeProductItemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val foodRecipeDetail = foodRecipeDetailsList[position]
        holder.itemRecipeProductItemBinding.itemNameTextView.text =
            foodRecipeDetail.title.toString().uppercase()
        holder.itemRecipeProductItemBinding.itemNameTextView.setOnClickListener {
            if (!holder.itemRecipeProductItemBinding.foodRecipeDetailsLayout.isVisible) {
                AnimationUtils.expand(holder.itemRecipeProductItemBinding.foodRecipeDetailsLayout)
                holder.itemRecipeProductItemBinding.itemNameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0)
                val ingredients=foodRecipeDetail.ingredients.replace("|",".\n\u2022\u0020")
                holder.itemRecipeProductItemBinding.ingredientsTextview.text = "• $ingredients"
                holder.itemRecipeProductItemBinding.servingsTextview.text = foodRecipeDetail.servings
                val instructions=foodRecipeDetail.instructions.replace(".",".\n\u2022\u0020")
                holder.itemRecipeProductItemBinding.instructionsTextview.text = instructions

            } else {
                //AnimationUtils.toggleExpand(false,holder.itemProductBinding.foodDetailsLayout)
                AnimationUtils.collapse(holder.itemRecipeProductItemBinding.foodRecipeDetailsLayout)
                holder.itemRecipeProductItemBinding.itemNameTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_baseline_expand_more_24,
                    0
                )
            }

        }
    }

    override fun getItemCount(): Int {
        return foodRecipeDetailsList.size
    }

    class ProductViewHolder(val itemRecipeProductItemBinding: ItemRecipeProductItemBinding) :
        RecyclerView.ViewHolder(itemRecipeProductItemBinding.root)
}