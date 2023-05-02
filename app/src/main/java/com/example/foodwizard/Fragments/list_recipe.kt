package com.example.foodwizard.Fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.recipeAdapter
import com.example.foodwizard.Meal
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListRecipeBinding


class list_recipe : Fragment() {
    private var _binding: FragmentListRecipeBinding? = null
    private lateinit var meals: MutableList<Meal>
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
        _binding = FragmentListRecipeBinding.inflate(inflater, container, false)
        binding.recipeRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.recipeRecyclerView.addItemDecoration(
            MarginItemDecoration(64)
        )
        meals= mutableListOf<Meal>(Meal("apple"),Meal("hamburger"))
        val adapter = recipeAdapter(meals){
            //findNavController().navigate(R.id.nav_detail_meal)
            val newFragment=detail_recipe()
            fragmentManager?.let { newFragment.show(it, "dialog") }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                Log.d("dada",position.toString())
                binding.recipeRecyclerView.adapter?.notifyItemRemoved(position)
                meals.removeAt(position)
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(binding.recipeRecyclerView)


        binding.recipeRecyclerView.adapter = adapter


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}