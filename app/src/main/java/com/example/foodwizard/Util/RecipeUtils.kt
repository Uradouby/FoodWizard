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

    fun getTodayNutrition(): MutableList<Nutrition> {
        var nutrition = mutableListOf<Nutrition>()
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "recipe unil get today global in")
            auth = FirebaseAuth.getInstance()

            val user: FirebaseUser? = auth.currentUser
            var currentUserId = user!!.uid
            // Get today's meal
            var todayMeal = usersViewModel.getTodayMeal(currentUserId, SimpleDateFormat("MM/dd/yyyy").format(
                Date()
            ))
            for(diet in todayMeal){
                diet.dietResponse?.nutrition?.let { nutrition.add(it) }
            }
        }
        Log.d(TAG, "recipe unil get today global out")
        return nutrition
    }

    fun getRecommendDiet(): MutableList<Diet> {
        var nutrition = getTodayNutrition()
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
        var diets = mutableListOf<Diet>()
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "recipe unil recommend global in")
            var meals = usersViewModel.getAllMeal()
            for(meal in meals){
                val calo = meal.dietResponse?.nutrition?.calories?.value
                val f = meal.dietResponse?.nutrition?.fat?.value
                val pro = meal.dietResponse?.nutrition?.protein?.value
                val carb = meal.dietResponse?.nutrition?.carbs?.value
                if (calo != null) {
                    if(f != null){
                        if(pro != null){
                            if(carb != null){
                                if(300 - calo <= calories && 100 - f <= fat && 50 - pro <= protein && 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(300 - calo <= calories && 100 - f <= fat && 50 - pro <= protein){
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(300 - calo <= calories && 100 - f <= fat && 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(300 - calo <= calories && 100 - f <= fat){
                                    diets.add(meal)
                                }
                            }
                        }
                    } else {
                        if(pro != null){
                            if(carb != null){
                                if(300 - calo <= calories&& 50 - pro <= protein && 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(300 - calo <= calories&& 50 - pro <= protein){
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(300 - calo <= calories&& 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(300 - calo <= calories){
                                    diets.add(meal)
                                }
                            }
                        }
                    }
                } else {
                    if(f != null){
                        if(pro != null){
                            if(carb != null){
                                if(100 - f <= fat && 50 - pro <= protein && 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(100 - f <= fat && 50 - pro <= protein){
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(100 - f <= fat && 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(100 - f <= fat){
                                    diets.add(meal)
                                }
                            }
                        }
                    } else {
                        if(pro != null){
                            if(carb != null){
                                if(50 - pro <= protein && 150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            } else {
                                if(50 - pro <= protein){
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(150 - carb <= carbs){
                                    diets.add(meal)
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.d(TAG, "recipe unil recommend global out")
        return diets
    }
}