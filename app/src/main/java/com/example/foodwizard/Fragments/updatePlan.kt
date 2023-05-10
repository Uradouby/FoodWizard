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
import com.example.foodwizard.databinding.FragmentUpdatePlanBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class updatePlan() : DialogFragment() {
    private var _binding: FragmentUpdatePlanBinding? = null
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
        _binding = FragmentUpdatePlanBinding.inflate(inflater, container, false)
        binding.apply{

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