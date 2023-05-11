package com.example.foodwizard.Fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwizard.Adapter.nutritionAdapter
import com.example.foodwizard.DB.Nutrient
import com.example.foodwizard.DB.Nutrition
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListNutritionBinding

class list_nutrition(nutrition: Nutrition) : DialogFragment() {
    private var _binding: FragmentListNutritionBinding? = null
    private var nutrition = nutrition
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListNutritionBinding.inflate(inflater, container, false)
        binding.nutritionRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.nutritionRecyclerView.addItemDecoration(
            MarginItemDecoration(64)
        )
        var nutrient_list = mutableListOf<Nutrient>(nutrition.calories,nutrition.fat,nutrition.protein,nutrition.carbs)
        val adapter = nutritionAdapter(nutrient_list)
        binding.nutritionRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}