package com.example.foodwizard.Util

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Nutrition
import com.example.foodwizard.DB.Repository
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RecipeUtils(val usersViewModel: UsersViewModel) {

    private lateinit var auth: FirebaseAuth
    private var nutrition = mutableListOf<Nutrition>()
//    private var diets = mutableListOf<Diet>()

    fun getTodayNutrition() {
        nutrition = mutableListOf<Nutrition>()
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "recipe unil get today global in")
            auth = FirebaseAuth.getInstance()

            val user: FirebaseUser? = auth.currentUser
            var currentUserId = user!!.uid
            // Get today's meal
            var todayMeal = usersViewModel.getTodayMeal(currentUserId, SimpleDateFormat("MM/dd/yyyy").format(
                Date()
            ))
            println("todayMeal: "+todayMeal)
            for(diet in todayMeal){
                val nu = diet.dietResponse?.nutrition
                if (nu != null) {
                    println("adding: " + diet)
                    nutrition.add(nu)
                    println("after adding: " + nutrition)
                }
            }
        }
        Log.d(TAG, "recipe unil get today global out")
        println("get nutrition: "+nutrition)
    }

    fun getRecommendDiet(): MutableList<Diet> {
        nutrition = mutableListOf<Nutrition>()
        var diets = mutableListOf<Diet>()
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "recipe unil get today global in")
            auth = FirebaseAuth.getInstance()

            val user: FirebaseUser? = auth.currentUser
            var currentUserId = user!!.uid
            // Get today's meal
            var todayMeal = usersViewModel.getTodayMeal(currentUserId, SimpleDateFormat("MM/dd/yyyy").format(
                Date()
            ))
            println("todayMeal: "+todayMeal)
            for(diet in todayMeal){
                val nu = diet.dietResponse?.nutrition
                if (nu != null) {
                    println("adding: " + diet)
                    nutrition.add(nu)
                    println("after adding: " + nutrition)
                }
            }
            println("today nutrition: " + nutrition)
            var calories = 0
            var fat = 0
            var protein = 0
            var carbs = 0
            for(nu in nutrition){
                calories += nu.calories.value
                fat += nu.fat.value
                protein += nu.protein.value
                carbs += nu.carbs.value
            }
            Log.d(TAG, "intake nutrition: " + calories + ", " + fat + ", " + protein + ", " + carbs)
            Log.d(TAG, "recipe unil recommend global in")
            var meals = usersViewModel.getAllMeal()
            for(meal in meals){
                val calo = meal.dietResponse?.nutrition?.calories?.value
                val f = meal.dietResponse?.nutrition?.fat?.value
                val pro = meal.dietResponse?.nutrition?.protein?.value
                val carb = meal.dietResponse?.nutrition?.carbs?.value
                Log.d(TAG, "get meal nutrition: " + calo + ", " + f + ", " + pro + ", " + carb)
                if (calo != null) {
                    if(f != null){
                        if(pro != null){
                            if(carb != null){
                                if(1000 - calo >= calories && 100 - f >= fat && 50 - pro >= protein && 150 - carb >= carbs){
                                    Log.d(TAG, "add 1" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(1000 - calo >= calories && 100 - f >= fat && 50 - pro >= protein){
                                    Log.d(TAG, "add 2" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(1000 - calo >= calories && 100 - f >= fat && 150 - carb >= carbs){
                                    Log.d(TAG, "add 3" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(1000 - calo >= calories && 100 - f >= fat){
                                    Log.d(TAG, "add 4" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    } else {
                        if(pro != null){
                            if(carb != null){
                                if(1000 - calo >= calories&& 50 - pro >= protein && 150 - carb >= carbs){
                                    Log.d(TAG, "add 5" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(1000 - calo >= calories&& 50 - pro >= protein){
                                    Log.d(TAG, "add 6" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(1000 - calo >= calories&& 150 - carb >= carbs){
                                    Log.d(TAG, "add 7" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(1000 - calo >= calories){
                                    Log.d(TAG, "add 8" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    }
                } else {
                    if(f != null){
                        if(pro != null){
                            if(carb != null){
                                if(100 - f >= fat && 50 - pro >= protein && 150 - carb >= carbs){
                                    Log.d(TAG, "add 9" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(100 - f >= fat && 50 - pro >= protein){
                                    Log.d(TAG, "add 10" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(100 - f >= fat && 150 - carb >= carbs){
                                    Log.d(TAG, "add 11" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(100 - f >= fat){
                                    Log.d(TAG, "add 12" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    } else {
                        if(pro != null){
                            if(carb != null){
                                if(50 - pro >= protein && 150 - carb >= carbs){
                                    Log.d(TAG, "add 13" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(50 - pro >= protein){
                                    Log.d(TAG, "add 14" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(150 - carb >= carbs){
                                    Log.d(TAG, "add 15" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    }
                }
            }
            println("in scope get diet: "+diets)
        }
        Log.d(TAG, "get diets: "+diets)
        return diets
    }
}