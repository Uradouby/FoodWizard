package com.example.foodwizard.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.foodwizard.R
import com.example.foodwizard.databinding.FragmentRecordBinding

class Record : Fragment() {
    private var _binding: FragmentRecordBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setFragmentResultListener("requestKey1") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("bundleKey1")
            Log.d("dadas", "addasdas")
            (childFragmentManager.findFragmentById(R.id.meals) as list_meal)?.updateview()
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        binding.addFab.setOnClickListener(){
            val newFragment=uploadmeal()
            fragmentManager?.let { newFragment.show(it, "dialog") }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}