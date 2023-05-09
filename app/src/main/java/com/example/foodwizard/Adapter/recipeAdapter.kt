package com.example.foodwizard.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.Meal
import com.example.foodwizard.databinding.ListItemMealBinding
import com.example.foodwizard.databinding.ListItemRecipeBinding

class recipeHolder(
    val binding: ListItemRecipeBinding
) : RecyclerView.ViewHolder(binding.root) {

}

class recipeAdapter(
    private val meals: MutableList<Diet>,
    private val onMealClicked: (Diet) -> Unit
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
                binding.food.visibility= View.VISIBLE
                binding.upload.visibility=View.INVISIBLE
                binding.name.visibility=View.VISIBLE
//                binding.time.visibility=View.VISIBLE
                binding.name.text = meal.dietTitle
//                binding.time.text = meal.date
                Glide.with(binding.food.context)
                    .load(meal.dietImage)
                    .into(binding.food)
                binding.root.setOnClickListener(){
                    onMealClicked(meal)
                }
            }

        }
        else
        {
            holder.apply{
                binding.food.visibility= View.VISIBLE
//                binding.upload.visibility=View.VISIBLE
                binding.name.visibility=View.VISIBLE
//                binding.time.visibility=View.GONE
                binding.name.visibility=View.GONE
            }
        }
    }
    override fun getItemCount() = meals.size
}