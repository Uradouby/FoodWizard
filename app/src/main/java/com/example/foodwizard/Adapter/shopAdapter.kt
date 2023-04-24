package com.example.foodwizard.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Meal
import com.example.foodwizard.databinding.ListItemMealBinding

class shopHolder(
    val binding: ListItemMealBinding
) : RecyclerView.ViewHolder(binding.root) {

}

class shopAdapter(
    private val meals: List<Meal>
) : RecyclerView.Adapter<shopHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : shopHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMealBinding.inflate(inflater, parent, false)
        return shopHolder(binding)
    }

    override fun onBindViewHolder(holder: shopHolder, position: Int) {
        val meal = meals[position]
        holder.apply {
            binding.food.visibility= View.INVISIBLE
            binding.upload.text=meal.Name
        }
    }
    override fun getItemCount() = meals.size
}