package com.example.foodwizard.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.Util.RecipeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val INITIALIZED = "INITIALIZED"

class RecipeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    lateinit var usersViewModel: UsersViewModel

    var meals: MutableList<Diet>
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: meals
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var initialized: Boolean
        get() = savedStateHandle.get(INITIALIZED) ?: false
        set(value) = savedStateHandle.set(INITIALIZED, value)

    fun update() {
        GlobalScope.launch(Dispatchers.IO) {
            meals = RecipeUtils(usersViewModel).getRecommendDiet()
        }
    }

    fun initialize(uvm: UsersViewModel){
        usersViewModel = uvm
        GlobalScope.launch(Dispatchers.IO) {
            if(!initialized){
                meals = RecipeUtils(usersViewModel).getRecommendDiet()
                initialized = true
            }
        }
    }
}