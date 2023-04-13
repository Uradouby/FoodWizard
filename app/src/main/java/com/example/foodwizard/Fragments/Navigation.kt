package com.example.foodwizard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

}