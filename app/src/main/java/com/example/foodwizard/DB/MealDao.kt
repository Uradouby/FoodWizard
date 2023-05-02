package com.example.foodwizard.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.Date

@Dao
interface MealDao {
    @Insert
    fun insertDiet(diet: Diet)

//    @Query("select * from dietsTable where userId = :userId AND date = :today")
@Query("select * from dietsTable where userId = :userId OR date = :today")
    fun getTodayMeal(userId: Int, today :String) : List<Diet>

@Query("select * from dietsTable where dietsTable.nutritioncaloriesvalue <= :calories " +
        "AND dietsTable.nutritionfatvalue <= :fat " +
        "AND dietsTable.nutritionproteinvalue <= :protein " +
        "AND dietsTable.nutritioncarbsvalue <= :carbs")
    fun getRecommendMeal(calories: Int, fat: Int, protein: Int, carbs: Int) : MutableList<Diet>

}