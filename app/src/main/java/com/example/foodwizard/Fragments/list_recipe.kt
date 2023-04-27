package com.example.foodwizard.Fragments

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.mealAdapter
import com.example.foodwizard.Adapter.recipeAdapter
import com.example.foodwizard.Meal
import com.example.foodwizard.R
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListMealBinding
import com.example.foodwizard.databinding.FragmentListRecipeBinding


class list_recipe : Fragment() {
    private var _binding: FragmentListRecipeBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListRecipeBinding.inflate(inflater, container, false)
        binding.recipeRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.recipeRecyclerView.addItemDecoration(
            MarginItemDecoration(64)
        )
        var meals= mutableListOf<Meal>(Meal("apple"),Meal("hamburger"))
        val adapter = recipeAdapter(meals)
        binding.recipeRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}