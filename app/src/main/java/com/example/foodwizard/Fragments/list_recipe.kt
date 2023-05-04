package com.example.foodwizard.Fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.mealAdapter
import com.example.foodwizard.Adapter.recipeAdapter
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.Meal
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListRecipeBinding
import com.example.foodwizard.viewModel.UsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class list_recipe : Fragment() {
    private var _binding: FragmentListRecipeBinding? = null
    private lateinit var meals: MutableList<Diet>
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
//        meals= mutableListOf<Meal>(Meal("apple"),Meal("hamburger"))
//        val adapter = recipeAdapter(meals){
//            //findNavController().navigate(R.id.nav_detail_meal)
//            val newFragment=detail_recipe()
//            fragmentManager?.let { newFragment.show(it, "dialog") }
//        }
//
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                // this method is called
//                // when the item is moved.
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                Log.d("dada",position.toString())
//                binding.recipeRecyclerView.adapter?.notifyItemRemoved(position)
//                meals.removeAt(position)
//            }
//            // at last we are adding this
//            // to our recycler view.
//        }).attachToRecyclerView(binding.recipeRecyclerView)
//
//
//        binding.recipeRecyclerView.adapter = adapter
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
            // TODO : userId swap to current userId
            // Get today's meal
            meals= usersViewModel.getRecommendMeal(20, 20, 20, 20)

            withContext(Dispatchers.Main) {
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
            }
        }
    }

}