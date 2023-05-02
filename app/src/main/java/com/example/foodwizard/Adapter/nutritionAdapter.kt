package com.example.foodwizard.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.DB.Nutrient
import com.example.foodwizard.DB.Nutrition
import com.example.foodwizard.Meal
import com.example.foodwizard.databinding.ListItemNutritionBinding

class NutritionHolder(
    val binding: ListItemNutritionBinding
) : RecyclerView.ViewHolder(binding.root) {

}
class nutritionAdapter(
    private val nutrient:  List<Nutrient>
) : RecyclerView.Adapter<NutritionHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : NutritionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNutritionBinding.inflate(inflater, parent, false)
        return NutritionHolder(binding)
    }

    override fun onBindViewHolder(holder: NutritionHolder, position: Int) {
        val nutrient = nutrient[position]
        val nutritionName = mutableListOf<String>("calories","fat","protein","carbs")
        holder.apply {
            binding.name.text = nutritionName[position]
            binding.value.text = nutrient.value.toString()
        }
    }

    override fun getItemCount() = nutrient.size
}