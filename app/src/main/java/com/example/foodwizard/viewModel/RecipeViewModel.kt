package com.example.foodwizard.viewModel

import android.app.Application
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Adapter.recipeAdapter
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Repository
import com.example.foodwizard.Fragments.detail_recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class RecipeViewModel(private val savedStateHandle: SavedStateHandle, val app: Application): ViewModel() {

    private var meals: MutableList<Diet>?
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: meals
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    init {
        update()
    }

    fun update() {
        GlobalScope.launch(Dispatchers.IO) {
            val repository = Repository.getInstance(app.applicationContext)
            meals = repository.getRecommendMeal(20, 20, 20, 20)
        }
    }
}