package com.example.foodwizard.Fragments
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.R
import com.example.foodwizard.databinding.DetailItemMealBinding
import com.example.foodwizard.databinding.FragmentListMealBinding
import com.example.foodwizard.databinding.FragmentListShopBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class detail_meal() : DialogFragment() {
    private var _binding: DetailItemMealBinding? = null
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
        _binding = DetailItemMealBinding.inflate(inflater, container, false)
        binding.apply{
            name.text=meal?.dietResponse?.category?.name
            possibility.text=meal?.dietResponse?.category?.probability.toString()
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
                Picasso.get().load(meal.dietImage).into(binding.food)
//                Glide.with(binding.food.context)
//                    .load(meal.dietImage)
//                    .into(binding.food)
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