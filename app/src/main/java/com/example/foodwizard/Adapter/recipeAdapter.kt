package com.example.foodwizard.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.Meal
import com.example.foodwizard.databinding.ListItemMealBinding
import com.example.foodwizard.databinding.ListItemRecipeBinding

class recipeHolder(
    val binding: ListItemRecipeBinding
) : RecyclerView.ViewHolder(binding.root) {

}

class recipeAdapter(
    private val meals: List<Diet>,
    private val onMealClicked: () -> Unit
) : RecyclerView.Adapter<recipeHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : recipeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRecipeBinding.inflate(inflater, parent, false)
        return recipeHolder(binding)
    }

    override fun onBindViewHolder(holder: recipeHolder, position: Int) {
        if (position<meals.size)
        {
            val meal = meals[position]
            holder.apply {
                binding.food.visibility= View.INVISIBLE
                binding.upload.text=meal.dietTitle
                binding.root.setOnClickListener(){
                    onMealClicked()
                }
            }

        }
        else
        {
            holder.apply{
                binding.root.setOnClickListener(){

                }
            }
        }
    }
    override fun getItemCount() = meals.size
}