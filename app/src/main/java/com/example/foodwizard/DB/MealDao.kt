package com.example.foodwizard.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.Date

@Dao
interface MealDao {
    @Insert
    fun insertDiet(diet: Diet)

    @Query("select * from dietsTable where userId = :userId AND date = :today")
    fun getTodayMeal(userId: String, today :String) : List<Diet>

}