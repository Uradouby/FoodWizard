package com.example.foodwizard.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.DB.Nutrient
import com.example.foodwizard.databinding.ListItemNutritionBinding

class NutritionHolder(
    val binding: ListItemNutritionBinding
) : RecyclerView.ViewHolder(binding.root) {

}
class nutritionAdapter(
    private val nutrients:  List<Nutrient>
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
        val nutrient = nutrients[position]
        val nutritionName = mutableListOf<String>("calories", "fat", "protein", "carbs")
        val horizontalBarColor = mutableListOf<String>("#4287f5", "#f542e0", "#42f554", "#f5bf42")
        val totalValue = nutrients[1].value + nutrients[2].value + nutrients[3].value
        val proportion = 200f / totalValue
        holder.apply {
            binding.name.text = nutritionName[position]
            if (nutritionName[position] == "calories") {
                binding.value.text = nutrient.value.toString()
                binding.bar.visibility = View.GONE
            } else {
                binding.value.text = "${nutrient.value}g"
                binding.bar.visibility = View.VISIBLE
                binding.bar.setBackgroundColor(Color.parseColor(horizontalBarColor[position]))
                binding.bar.layoutParams.width = (nutrient.value * proportion).toInt()
            }
        }
    }

    override fun getItemCount() = nutrients.size
}