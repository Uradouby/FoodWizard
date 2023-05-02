package com.example.foodwizard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwizard.Adapter.shopAdapter
import com.example.foodwizard.Meal
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListShopBinding


class list_shop : Fragment() {
    private var _binding: FragmentListShopBinding? = null
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
        _binding = FragmentListShopBinding.inflate(inflater, container, false)
        binding.shopRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.shopRecyclerView.addItemDecoration(
            MarginItemDecoration(64)
        )
        var meals= mutableListOf<Meal>(Meal("apple$1"),Meal("hamburger$2"))
        val adapter = shopAdapter(meals)
        binding.shopRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}