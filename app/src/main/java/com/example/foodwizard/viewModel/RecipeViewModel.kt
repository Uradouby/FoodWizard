package com.example.foodwizard.viewModel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

var lower = 0
var higher = 5

class RecipeViewModel(private val savedStateHandle: SavedStateHandle, val app: Application): ViewModel() {

    var meals: MutableList<Diet>
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: meals
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val repository = Repository.getInstance(app.applicationContext)
            meals = repository.getRecommendMeal(lower, higher)
        }
    }

    fun update() {
        GlobalScope.launch(Dispatchers.IO) {
            lower = lower + 5
            higher = higher + 5
            val repository = Repository.getInstance(app.applicationContext)
            meals = repository.getRecommendMeal(lower, higher)
        }
    }
}