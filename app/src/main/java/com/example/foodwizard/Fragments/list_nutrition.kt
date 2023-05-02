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
import com.example.foodwizard.Meal
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListNutritionBinding

class list_nutrition() : DialogFragment() {
    private var _binding: FragmentListNutritionBinding? = null
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
        var meals= mutableListOf<Meal>(Meal("apple"), Meal("hamburger"))
        val adapter = nutritionAdapter(meals)
        binding.nutritionRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}