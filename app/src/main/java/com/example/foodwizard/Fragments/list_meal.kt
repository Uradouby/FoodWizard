package com.example.foodwizard.Fragments

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.mealAdapter
import com.example.foodwizard.R
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListMealBinding
import com.example.foodwizard.login
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class list_meal : Fragment()  {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentListMealBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("SimpleDateFormat")
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
            // var meals= mutableListOf<Meal>()
        auth = FirebaseAuth.getInstance()
        updateview()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateview()
    {
        GlobalScope.launch(Dispatchers.IO) {
            val usersViewModel: UsersViewModel by activityViewModels()
            val user: FirebaseUser? = auth.currentUser
            var currentUserId = user!!.uid
            // Get today's meal
            var todayMeal = usersViewModel.getTodayMeal(currentUserId, SimpleDateFormat("MM/dd/yyyy").format(Date()))

            withContext(Dispatchers.Main) {
                val adapter = mealAdapter(todayMeal) {meal ->
                    //findNavController().navigate(R.id.nav_detail_meal)
                    val newFragment=detail_meal()
                    val bundle = Bundle().apply {
                        putSerializable("meal", meal)
                    }
                    newFragment.arguments = bundle
                    fragmentManager?.let { newFragment.show(it, "dialog") }
                }
                binding.mealRecyclerView.adapter = adapter
            }
        }
    }


}