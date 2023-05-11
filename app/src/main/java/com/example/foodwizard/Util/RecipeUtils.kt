package com.example.foodwizard.Util

import android.content.ContentValues.TAG
import android.util.Log
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Nutrition
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class RecipeUtils(val usersViewModel: UsersViewModel) {

    private lateinit var auth: FirebaseAuth
    private var nutrition = mutableListOf<Nutrition>()
    private lateinit var userReference: DatabaseReference
    private var planCalo = -1
    private var planFat = -1
    private var planProtein = -1
    private var planCarbs = -1

    fun getRecommendDiet(): MutableList<Diet> {
        nutrition = mutableListOf()
        var diets = mutableListOf<Diet>()
        var added = false
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "recipe unil get today global in")
            auth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = auth.currentUser
            var currentUserId = user!!.uid
            userReference =
                FirebaseDatabase.getInstance().getReference("Users").child(currentUserId)
            userReference.child("calory").get().addOnSuccessListener {
                Log.i("firebase read", "util Got value ${it.value}")
                planCalo = it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "util Error getting data", it)
            }
            userReference.child("fat").get(). addOnSuccessListener{
                Log.i("firebase read", "util Got value ${it.value}")
                planFat = it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "util Error getting data", it)
            }
            userReference.child("protein").get().addOnSuccessListener {
                Log.i("firebase read", "util Got value ${it.value}")
                planProtein = it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "util Error getting data", it)
            }
            userReference.child("carb").get().addOnSuccessListener {
                Log.i("firebase read", "util Got value ${it.value}")
                planCarbs = it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "util Error getting data", it)
            }
            while(planCalo == -1 || planFat == -1 || planProtein == -1 || planCarbs == -1){

            }
            // Get today's meal
            var todayMeal = usersViewModel.getTodayMeal(currentUserId, SimpleDateFormat("MM/dd/yyyy").format(
                Date()
            ))
            for(diet in todayMeal){
                val nu = diet.dietResponse?.nutrition
                if (nu != null) {
                    nutrition.add(nu)
                }
            }
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
            Log.d(TAG, "all meals: " + meals.size)
            for(meal in meals){
                val calo = meal.dietResponse?.nutrition?.calories?.value
                val f = meal.dietResponse?.nutrition?.fat?.value
                val pro = meal.dietResponse?.nutrition?.protein?.value
                val carb = meal.dietResponse?.nutrition?.carbs?.value
                Log.d(TAG, "get meal nutrition: " + calo + ", " + f + ", " + pro + ", " + carb)
                Log.d(TAG, "get plan nutrition: " + planCalo + ", " + planFat + ", " + planProtein + ", " + planCarbs)
                if (calo != null) {
                    if(f != null){
                        if(pro != null){
                            if(carb != null){
                                if(planCalo - calo >= calories && planFat - f >= fat && planProtein - pro >= protein && planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 1" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planCalo - calo >= calories && planFat - f >= fat && planProtein - pro >= protein){
                                    Log.d(TAG, "add 2" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(planCalo - calo >= calories && planFat - f >= fat && planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 3" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planCalo - calo >= calories && planFat - f >= fat){
                                    Log.d(TAG, "add 4" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    } else {
                        if(pro != null){
                            if(carb != null){
                                if(planCalo - calo >= calories&& planProtein - pro >= protein && planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 5" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planCalo - calo >= calories&& planProtein - pro >= protein){
                                    Log.d(TAG, "add 6" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(planCalo - calo >= calories&& planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 7" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planCalo - calo >= calories){
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
                                if(planFat - f >= fat && planProtein - pro >= protein && planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 9" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planFat - f >= fat && planProtein - pro >= protein){
                                    Log.d(TAG, "add 10" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(planFat - f >= fat && planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 11" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planFat - f >= fat){
                                    Log.d(TAG, "add 12" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    } else {
                        if(pro != null){
                            if(carb != null){
                                if(planProtein - pro >= protein && planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 13" + diets)
                                    diets.add(meal)
                                }
                            } else {
                                if(planProtein - pro >= protein){
                                    Log.d(TAG, "add 14" + diets)
                                    diets.add(meal)
                                }
                            }
                        } else {
                            if(carb != null){
                                if(planCarbs - carb >= carbs){
                                    Log.d(TAG, "add 15" + diets)
                                    diets.add(meal)
                                }
                            }
                        }
                    }
                }
            }
            added = true
            Log.d(TAG, "in scope get diet: "+ diets)
        }
        while(!added){

        }
        Log.d(TAG, "get diets: "+ diets)
        return diets
    }
}