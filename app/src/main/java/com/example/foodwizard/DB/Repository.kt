package com.example.foodwizard.DB

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import java.util.*

class Repository private constructor(context: Context){
    private val usersDao = AppDataBase.getAppDataBase(context).getUserDao()
    private val mealDao = AppDataBase.getAppDataBase(context).getMealDao()
    companion object {
        private lateinit var instance: Repository

        fun getInstance(context: Context): Repository {
            if (!Companion::instance.isInitialized) {
                instance = Repository(context)
            }
            return instance
        }
    }

//    User Repository
    fun getAllUsers(): LiveData<List<User>> {
    return usersDao.getAllUsers()
    }

    fun addUser(user: User) {
        usersDao.insertUser(user)
    }

    fun getUserById(userId: Int): User {
        return usersDao.getUserById(userId)
    }

    fun getUserByUsername(userName: String): User {
        return usersDao.getUserByUsername(userName)
    }

    fun updateUserName(userId: Int, userName: String) {
        usersDao.updateUserName(userId, userName)
    }

    fun updateUserPassword(userId: Int, password: String) {
        usersDao.updateUserPassword(userId, password)
    }

    @Insert
    fun insertDiet(diet: Diet){
        mealDao.insertDiet(diet)
    }

    fun getTodayMeal(userId: Int,today: String) : List<Diet>{
        return mealDao.getTodayMeal(userId,today)
    }
}