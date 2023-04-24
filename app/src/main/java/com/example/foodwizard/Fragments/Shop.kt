package com.example.foodwizard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwizard.Adapter.mealAdapter
import com.example.foodwizard.Meal
import com.example.foodwizard.R
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentRecordBinding
import com.example.foodwizard.databinding.FragmentShopBinding

class Shop : Fragment() {
    private var _binding: FragmentShopBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}