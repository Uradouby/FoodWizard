package com.example.foodwizard.Fragments

import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.R
import com.example.foodwizard.databinding.DetailItemRecipeBinding
import com.squareup.picasso.Picasso

class detail_recipe : DialogFragment(){
    private var _binding: DetailItemRecipeBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val meal = arguments?.getSerializable("meal") as Diet?
        Log.d(ContentValues.TAG, "detail diet: " + meal)
        _binding = DetailItemRecipeBinding.inflate(inflater, container, false)
        binding.apply{
            name.text=meal?.dietResponse?.category?.name
//            possibility.text=meal?.dietResponse?.category?.probability.toString()  //TODO: CHANGE THAT it's not possibility but description
            description.text = meal?.description
            nutritionFab.setOnClickListener(){
                val newFragment= meal?.dietResponse?.nutrition?.let { it1 -> list_nutrition(it1) }
                fragmentManager?.let {
                    if (newFragment != null) {
                        newFragment.show(it, "nutrition")
                    }
                }
            }

            if (meal != null) {
                Log.d(ContentValues.TAG, "load image: "+meal.dietImage)
                Picasso.get().load(meal.dietImage).into(binding.food)
            }
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}