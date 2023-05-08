package com.example.foodwizard.viewModel

import android.app.Application
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Repository
import com.example.foodwizard.Util.RecipeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class RecipeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

//    init {
//        GlobalScope.launch(Dispatchers.IO) {
//            val usersViewModel: UsersViewModel by activityViewModels()
//            meals = RecipeUtils(usersViewModel).getRecommendDiet()
//        }
//    }
//
//    var meals: MutableList<Diet>
//        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: meals
//        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
//
//    fun update() {
//        GlobalScope.launch(Dispatchers.IO) {
//            val usersViewModel: UsersViewModel by activityViewModels()
//            meals = RecipeUtils(usersViewModel).getRecommendDiet()
//        }
//    }
}