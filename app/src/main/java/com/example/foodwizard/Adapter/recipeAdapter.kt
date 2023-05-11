package com.example.foodwizard.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.SimpleMeal
import com.example.foodwizard.Util.RecipeUtils.Companion.getMeals
import com.example.foodwizard.databinding.ListItemMealBinding
import com.example.foodwizard.databinding.ListItemRecipeBinding
import com.squareup.picasso.Picasso
import java.lang.Integer.max

class recipeHolder(
    val binding: ListItemRecipeBinding
) : RecyclerView.ViewHolder(binding.root) {

}

class recipeAdapter(
    private val meals: MutableList<SimpleMeal>,
    private val onMealClicked: (SimpleMeal) -> Unit
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
                binding.name.text = meal.Name
//                binding.time.text = meal.date
                Log.d("Imagepath",meal.ImagePath)
                Picasso.get().load(meal.ImagePath).into(binding.food)
                binding.root.setOnClickListener(){
                    onMealClicked(meal)
                }
            }

        }

    }
    override fun getItemCount() =meals.size
}