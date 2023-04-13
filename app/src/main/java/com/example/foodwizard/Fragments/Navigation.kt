package com.example.foodwizard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.foodwizard.R
import com.example.foodwizard.databinding.FragmentNavigationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [Navigation.newInstance] factory method to
 * create an instance of this fragment.
 */
class Navigation : Fragment() {
    private var _binding: FragmentNavigationBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    fun InativeAll()
    {
        binding.camera.setImageResource(R.drawable.inactive_camera);
        binding.recipe.setImageResource(R.drawable.inactive_recipe);
        binding.shop.setImageResource(R.drawable.inactive_shop);
        binding.personalinfo.setImageResource(R.drawable.inactive_personalinfo);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        binding.camera.setOnClickListener{
            val result= "camera"
            InativeAll()
            binding.camera.setImageResource(R.drawable.active_camera);
            setFragmentResult("MainActivity", bundleOf("bundleKey" to result))
        }
        binding.recipe.setOnClickListener{
            val result= "recipe"
            InativeAll()
            binding.recipe.setImageResource(R.drawable.active_recipe);
            setFragmentResult("MainActivity", bundleOf("bundleKey" to result))
        }
        binding.shop.setOnClickListener{
            val result= "shop"
            InativeAll()
            binding.shop.setImageResource(R.drawable.active_shop);
            setFragmentResult("MainActivity", bundleOf("bundleKey" to result))
        }
        binding.personalinfo.setOnClickListener{
            val result= "peronalinfo"
            InativeAll()
            binding.personalinfo.setImageResource(R.drawable.active_personalinfo);
            setFragmentResult("MainActivity", bundleOf("bundleKey" to result))
        }
        return binding.root
    }

}