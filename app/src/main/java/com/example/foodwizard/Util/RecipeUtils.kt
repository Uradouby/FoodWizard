package com.example.foodwizard.Util

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.DB.Nutrition
import com.example.foodwizard.SimpleMeal
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt


class RecipeUtils(val usersViewModel: UsersViewModel) {

    private lateinit var auth: FirebaseAuth
    private var nutrition = mutableListOf<Nutrition>()
    private lateinit var userReference: DatabaseReference
    private var planCalo=usersViewModel.plancalory
        get()= usersViewModel.plancalory
    private var planFat = usersViewModel.planfat
        get()=usersViewModel.planfat
    private var planProtein = usersViewModel.planprotein
        get()=usersViewModel.planprotein
    private var planCarbs = usersViewModel.plancarb
        get()=usersViewModel.plancarb

    companion object{
        private var meals= mutableListOf<SimpleMeal>()
        fun getMeals():MutableList<SimpleMeal>
        {
            if (meals.size!=0) return meals
            var diets = mutableListOf<SimpleMeal>()

                var mealReference:DatabaseReference=FirebaseDatabase.getInstance().getReference("Meal")
                mealReference.get().addOnSuccessListener {

                    for (child in it.getChildren())
                    {
                        var i = 0
                        var fat = 0
                        var carb = 0
                        var protein = 0
                        var calory = 0
                        var name = ""
                        var imagepath = ""
                        //Log.d("1",child.value.toString())
                        carb=child.value.toString().split("carb=")[1].split(",")[0].toInt()
                        fat=child.value.toString().split("fat=")[1].split(",")[0].toInt()
                        protein=child.value.toString().split("protein=")[1].split(",")[0].toInt()
                        calory=child.value.toString().split("calory=")[1].split(",")[0].toInt()
                        imagepath=child.value.toString().split("imagepath=")[1].split(",")[0]
                        name=child.value.toString().split("name=")[1].split("}")[0]
                        Log.d("1",carb.toString()+" "+fat.toString()+" "+protein.toString()+" "+calory.toString()+" "+imagepath+" "+name)
                        var tmpmeal= SimpleMeal(name,fat,carb,protein,calory,imagepath)
                        diets.add(tmpmeal)
                    }
//                val fat=mealReference.child("fat").get().toString()
//                val protein=mealReference.child("protein").get().toString()
//                val carb=mealReference.child("carb").get().toString().toInt()
//                val calory=mealReference.child("calory").get().toString().toInt()
//                val name:String=""
//                mealReference.child("name").get().addOnSuccessListener {
//                    Log.d("1",it.value.toString())
//                }
//                val imagepath=mealReference.child("imagepath").get().toString()
            }
            meals=diets
            return diets
        }
    }
    fun getRecommendDiet(): MutableList<SimpleMeal> {
        nutrition = mutableListOf()
        var diets = mutableListOf<SimpleMeal>()
        //GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "recipe unil get today global in")
            auth = FirebaseAuth.getInstance()
            Log.d(TAG, "recipe unil get today global in!!!")
            val user: FirebaseUser? = auth.currentUser
            Log.d(TAG, "recipe unil get today global in!!!!")
            var currentUserId = user!!.uid

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
            val meals = getMeals()
            Log.d(TAG, "all meals: " + meals.size)
            for(meal in meals){
                val calo = meal.Calory
                val f = meal.Fat
                val pro = meal.Protein
                val carb = meal.Carb
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
            //}
            Log.d(TAG, "in scope get diet: "+ diets)
        }

        Log.d(TAG, "get diets: "+ diets)
        return diets
    }
}