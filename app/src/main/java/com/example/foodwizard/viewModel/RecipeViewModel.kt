package com.example.foodwizard.viewModel

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.recipeAdapter
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.Fragments.detail_recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    lateinit var meals: MutableList<Diet>


    fun update()
    {
        GlobalScope.launch(Dispatchers.IO) {
            val usersViewModel: UsersViewModel by activityViewModels()
            var meals = usersViewModel.getRecommendMeal(20, 20, 20, 20)
        }
    }
}