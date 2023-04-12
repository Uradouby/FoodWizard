package com.example.foodwizard.Diet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.databinding.FragmentListDietBinding



class DietList : Fragment() {
    private var _binding: FragmentListDietBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private lateinit var adapter: DietAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDietBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DietAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        val dietList = mutableListOf<DietResponse>()
        for (i in 0..10) {
            dietList.add(DietResponse(List(i) { Diet("Result $i", "Image $i") }))
        }
        adapter.submitList(dietList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

