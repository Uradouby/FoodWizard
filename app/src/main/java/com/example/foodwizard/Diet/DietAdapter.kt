package com.example.foodwizard.Diet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.databinding.DetailItemMealBinding

class DietAdapter : ListAdapter<DietResponse, DietAdapter.DietViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DietViewHolder {
        return DietViewHolder(DetailItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DietViewHolder(private val binding: DetailItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // get the dietResponse
        fun bind(dietResponse: DietResponse) {
            // get the category
            val category = dietResponse.category_response
            binding.name.text = category.name
            binding.possibility.text = category.probability.toString()

            // get nutrition list
            var nutritionResultText = ""
            for (nutrition in dietResponse.nutrition_response) {
                nutritionResultText += "${nutrition.nutritionTitle}: ${nutrition.nutritionValue}\n"
            }
            binding.description.text = nutritionResultText
        }
    }

    object DIFF_CALLBACK : DiffUtil.ItemCallback<DietResponse>() {
        // whether the items are the same
        override fun areItemsTheSame(oldItem: DietResponse, newItem: DietResponse) =
            oldItem.id == newItem.id
        // whether contents are the same
        override fun areContentsTheSame(oldItem: DietResponse, newItem: DietResponse) =
            oldItem == newItem
    }
}
