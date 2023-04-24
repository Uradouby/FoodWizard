package com.example.foodwizard.Fragments

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.mealAdapter
import com.example.foodwizard.Meal
import com.example.foodwizard.R
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListMealBinding


class list_meal : Fragment() {
    private var _binding: FragmentListMealBinding? = null
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
        _binding = FragmentListMealBinding.inflate(inflater, container, false)
        binding.mealRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.mealRecyclerView.addItemDecoration(
            MarginItemDecoration(64)
        )
        var meals= mutableListOf<Meal>(Meal("apple"),Meal("hamburger"))
        val adapter = mealAdapter(meals) {
            //findNavController().navigate(R.id.nav_detail_meal)
            val newFragment=detail_meal()
            fragmentManager?.let { newFragment.show(it, "dialog") }
        }
        binding.mealRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}